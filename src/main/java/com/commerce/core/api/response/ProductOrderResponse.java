package com.commerce.core.api.response;

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
}
