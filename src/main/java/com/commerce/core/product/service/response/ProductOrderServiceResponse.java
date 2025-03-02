package com.commerce.core.product.service.response;

import com.commerce.core.api.response.ProductOrderResponse;
import lombok.Builder;

@Builder
public record ProductOrderServiceResponse(
        Long productSeq,
        String productOptionCode,
        Long productInfoSeq,
        String productName,
        String productDetail,
        Long price
) {
    public ProductOrderResponse toResponse() {
        return ProductOrderResponse.builder()
                .productSeq(productSeq)
                .productOptionCode(productOptionCode)
                .productInfoSeq(productInfoSeq)
                .productName(productName)
                .productDetail(productDetail)
                .price(price)
                .build();
    }
}
