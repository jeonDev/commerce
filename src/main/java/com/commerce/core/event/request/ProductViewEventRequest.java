package com.commerce.core.event.request;

import com.commerce.core.product.type.ProductViewStatus;
import lombok.Builder;

@Builder
public record ProductViewEventRequest(
        Long productInfoSeq,
        ProductViewStatus productViewStatus
) {
}
