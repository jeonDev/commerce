package com.commerce.core.api.request;

import com.commerce.core.product.service.request.ProductStockServiceRequest;
import com.commerce.core.product.vo.ProductStockProcessStatus;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record ProductStockRequest(
        @NotNull
        Long productSeq,
        @Min(0) @NotNull Long stock,
        @NotNull
        ProductStockProcessStatus productStockProcessStatus
) {
    public ProductStockServiceRequest toRequest() {
        return ProductStockServiceRequest.builder()
                .productSeq(productSeq)
                .stock(stock)
                .productStockProcessStatus(productStockProcessStatus)
                .build();
    }
}
