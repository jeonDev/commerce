package com.commerce.core.api.response;

import com.commerce.core.product.service.response.ProductOrderServiceResponse;
import lombok.Builder;

@Builder
public record ProductOrderResponse(
        Long productSeq,
        String productOptionCode,
        Long productInfoSeq,
        String productName,
        String productDetail,
        Long price
) {
    public static ProductOrderResponse from(ProductOrderServiceResponse response) {
        return ProductOrderResponse.builder()
                .productSeq(response.productSeq())
                .productOptionCode(response.productOptionCode())
                .productInfoSeq(response.productSeq())
                .productName(response.productName())
                .productDetail(response.productDetail())
                .price(response.price())
                .build();
    }
}
