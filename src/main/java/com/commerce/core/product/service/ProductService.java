package com.commerce.core.product.service;

import com.commerce.core.common.exception.CommerceException;
import com.commerce.core.common.exception.ExceptionStatus;
import com.commerce.core.product.domain.ProductDao;
import com.commerce.core.product.domain.entity.Product;
import com.commerce.core.product.domain.entity.ProductInfo;
import com.commerce.core.product.service.request.ProductServiceRequest;
import com.commerce.core.product.type.ProductViewStatus;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
public class ProductService {

    private final ProductDao productDao;
    private final ApplicationEventPublisher publisher;

    public ProductService(ProductDao productDao,
                          ApplicationEventPublisher publisher) {
        this.productDao = productDao;
        this.publisher = publisher;
    }

    @Transactional
    public ProductInfo add(ProductServiceRequest request) {
        // 1. 상품 정보 조회
        var productInfo = this.mergeProductInfo(request);

        // 2. 상품 save
        var list = request.productOptions()
                .stream()
                .map(s -> Product.of(productInfo, s))
                .toList();
        productDao.saveAll(list);

        // 3. Event Producer Push
        publisher.publishEvent(productInfo.productMakeEventPublisherRequest(ProductViewStatus.REGISTER));

        return productInfo;
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
