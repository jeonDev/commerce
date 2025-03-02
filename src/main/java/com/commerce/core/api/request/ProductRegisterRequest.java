package com.commerce.core.api.request;

import com.commerce.core.product.service.request.ProductServiceRequest;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record ProductRegisterRequest(
        Long productInfoSeq,
        @NotNull String productName,
        @NotNull String productDetail,
        @Min(0) @NotNull Long price,
        List<String> productOptions
) {
    public ProductServiceRequest toRequest() {

        return ProductServiceRequest.builder()
                .build();
    }
}
