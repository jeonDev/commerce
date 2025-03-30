package com.commerce.core.product.type;

import lombok.Builder;

@Builder
public record ProductOptions(
        Long productSeq,
        String productOption
) {

}
