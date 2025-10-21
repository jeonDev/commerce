package com.commerce.core.event.request;

import com.commerce.core.product.type.ProductViewStatus;

public record ProductEventRequest(
        Long productInfoSeq,
        ProductViewStatus productViewStatus
) {
}
