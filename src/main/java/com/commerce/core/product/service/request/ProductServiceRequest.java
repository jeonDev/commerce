package com.commerce.core.product.service.request;

import com.commerce.core.product.domain.entity.ProductInfo;
import lombok.Builder;

import java.util.List;

@Builder
public record ProductServiceRequest(
        Long productInfoSeq,
        String productName,
        String productDetail,
        Long price,
        List<String> productOptions
) {

    public ProductInfo requestToEntity() {
        return ProductInfo.builder()
                .productInfoSeq(this.productInfoSeq)
                .productName(this.productName)
                .productDetail(this.productDetail)
                .price(this.price)
                .build();
    }
}
