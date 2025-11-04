package com.commerce.core.product.facade.response;

import lombok.Builder;

@Builder
public record ProductServiceResponse(
        Long productInfoSeq,
        String productName,
        String productDetail,
        Long price
) {
}
