package com.commerce.core.product.service.response;

import lombok.Builder;

@Builder
public record ProductServiceResponse(
        Long productInfoSeq,
        String productName,
        String productDetail,
        Long price
) {
}
