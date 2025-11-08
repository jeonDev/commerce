package com.commerce.core.event.request;

import com.commerce.core.product.domain.type.ProductViewStatus;

public record ProductEventRequest(
        Long productInfoSeq,
        ProductViewStatus productViewStatus
) {
}
