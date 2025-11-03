package com.commerce.core.product.domain.type;

import lombok.Builder;

@Builder
public record ProductOptions(
        Long productSeq,
        String productOption
) {

}
