package com.commerce.core.product.service.request;

import com.commerce.core.product.type.ProductViewStatus;
import lombok.Builder;

@Builder
public record ProductViewServiceRequest(
        Long productInfoSeq,
        ProductViewStatus productViewStatus
) {
}
