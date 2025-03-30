package com.commerce.core.product.service.request;

import com.commerce.core.product.type.ProductStockProcessStatus;
import lombok.Builder;

@Builder
public record ProductStockServiceRequest(
        Long productSeq,
        Long stock,
        ProductStockProcessStatus productStockProcessStatus
) {

}
