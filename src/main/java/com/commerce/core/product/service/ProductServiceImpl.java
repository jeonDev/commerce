package com.commerce.core.product.service;

import com.commerce.core.common.exception.CommerceException;
import com.commerce.core.common.exception.ExceptionStatus;
import com.commerce.core.event.EventTopic;
import com.commerce.core.event.producer.EventSender;
import com.commerce.core.product.entity.Product;
import com.commerce.core.product.entity.ProductInfo;
import com.commerce.core.product.repository.dsl.ProductDslRepository;
import com.commerce.core.product.repository.ProductRepository;
import com.commerce.core.product.vo.ProductDto;
import com.commerce.core.product.vo.ProductInfoDto;
import com.commerce.core.product.vo.ProductViewDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
//    private final ProductDslRepository productDslRepository;

    private final ProductInfoService productInfoService;

    private final EventSender eventSender;

    /**
     * Product Add
     */
    @Transactional
    @Override
    public Product add(ProductDto dto) {
        // 1. 상품 정보 조회
        ProductInfo productInfo = this.mergeProductInfo(dto.getProductInfoDto());

        // 2. 상품 save
        Product product = productRepository.save(dto.dtoToEntity(productInfo));

        // 3. Event Producer Push
        ProductViewDto productViewDto = ProductViewDto.builder()
                .productInfoSeq(productInfo.getProductInfoSeq())
                .productName(productInfo.getProductName())
                .productDetail(productInfo.getProductDetail())
                .price(productInfo.getPrice())
                .useYn("Y")
                .productOptions(List.of(product.getProductOptionCode()))
                .build();
        eventSender.send(EventTopic.SYNC_PRODUCT.getTopic(), productViewDto);

        return product;
    }

    /**
     * Select Product Detail
     */
    @Override
    public Optional<Product> selectProduct(Long productSeq) {
        return productRepository.findById(productSeq);
    }

    /**
     * Product Info Setting
     */
    private ProductInfo mergeProductInfo(ProductInfoDto dto) {
        // 1-1. 상품 정보 존재 시, 세팅
        Long productInfoSeq = dto.getProductInfoSeq();
        if(productInfoSeq == null) {
            return productInfoService.add(dto);
        }
        // 1-2. 상품 정보 없을 시, 등록 및 세팅
        return productInfoService.selectProductInfo(productInfoSeq)
                .orElseThrow(() -> new CommerceException(ExceptionStatus.ENTITY_IS_EMPTY));
    }
}
