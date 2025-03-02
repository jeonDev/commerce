package com.commerce.core.product.service.request;

import com.commerce.core.product.vo.ProductViewStatus;
import lombok.Builder;

@Builder
public record ProductViewServiceRequest(
        Long productInfoSeq,
        ProductViewStatus productViewStatus
) {
}
