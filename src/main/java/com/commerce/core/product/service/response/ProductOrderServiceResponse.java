package com.commerce.core.product.service.response;

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
}
