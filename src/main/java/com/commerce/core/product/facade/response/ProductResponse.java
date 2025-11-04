package com.commerce.core.product.facade.response;

import lombok.Builder;

import java.util.List;

@Builder
public record ProductResponse(
        Long productSeq,
        String productName,
        String productDescription,
        List<ProductOptionResponse> productOptionList
) {

    public record ProductOptionResponse(
            Long productOptionSeq,
            String productOptionName,
            String productOptionDescription,
            Long price,
            Long stock
    ) {

    }
}
