package com.commerce.core.product.service.request;

import com.commerce.core.product.vo.ProductStockProcessStatus;
import lombok.Builder;

@Builder
public record ProductStockServiceRequest(
        Long productSeq,
        Long stock,
        ProductStockProcessStatus productStockProcessStatus
) {

}
