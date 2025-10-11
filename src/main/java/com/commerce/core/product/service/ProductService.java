package com.commerce.core.product.service;

import com.commerce.core.common.exception.CommerceException;
import com.commerce.core.common.exception.ExceptionStatus;
import com.commerce.core.event.EventTopic;
import com.commerce.core.event.producer.EventSender;
import com.commerce.core.product.domain.ProductDao;
import com.commerce.core.product.domain.entity.Product;
import com.commerce.core.product.domain.entity.ProductInfo;
import com.commerce.core.product.service.request.ProductServiceRequest;
import com.commerce.core.product.service.response.ProductServiceResponse;
import com.commerce.core.product.service.request.ProductViewServiceRequest;
import com.commerce.core.product.type.ProductViewStatus;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
public class ProductService {

    private final ProductDao productDao;
    private final EventSender eventSender;

    public ProductService(ProductDao productDao,
                          EventSender eventSender) {
        this.productDao = productDao;
        this.eventSender = eventSender;
    }

    @Transactional
    public ProductServiceResponse add(ProductServiceRequest request) {
        // 1. 상품 정보 조회
        var productInfo = this.mergeProductInfo(request);

        // 2. 상품 save
        var list = request.productOptions()
                .stream()
                .map(s -> Product.builder()
                        .productInfo(productInfo)
                        .productOptionCode(s)
                        .build())
                .toList();
        productDao.saveAll(list);

        // 3. Event Producer Push
        var productViewDto = ProductViewServiceRequest.builder()
                .productInfoSeq(productInfo.getProductInfoSeq())
                .productViewStatus(ProductViewStatus.REGISTER)
                .build();
        eventSender.send(EventTopic.SYNC_PRODUCT, productViewDto);

        return ProductServiceResponse.builder()
                .productInfoSeq(productInfo.getProductInfoSeq())
                .productName(productInfo.getProductName())
                .productDetail(productInfo.getProductDetail())
                .price(productInfo.getPrice())
                .build();
    }

    private ProductInfo mergeProductInfo(ProductServiceRequest request) {
        // 1-1. 상품 정보 존재 시, 세팅
        Long productInfoSeq = request.productInfoSeq();
        if (productInfoSeq == null) {
            return productDao.productInfoSave(request.requestToEntity());
        }
        // 1-2. 상품 정보 없을 시, 등록 및 세팅
        var productInfo = productDao.findProductInfoById(productInfoSeq)
                .orElseThrow(() -> new CommerceException(ExceptionStatus.ENTITY_IS_EMPTY));
        productInfo.update(request.productName(), request.productDetail(), request.price());
        return productInfo;
    }
}
