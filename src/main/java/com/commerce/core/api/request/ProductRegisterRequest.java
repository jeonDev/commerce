package com.commerce.core.api.request;

import com.commerce.core.product.service.request.ProductServiceRequest;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record ProductRegisterRequest(
        @Schema(description = "상품번호", example = "1") Long productInfoSeq,
        @Schema(description = "상품이름", example = "상푸움") @NotNull String productName,
        @Schema(description = "상품 상세 설명", example = "이것은 뭐야") @NotNull String productDetail,
        @Schema(description = "상품 가격", example = "10000") @Min(0) @NotNull Long price,
        @Schema(description = "상품 옵션", example = "{'A', 'B'}") List<String> productOptions
) {
    public ProductServiceRequest toRequest() {
        return ProductServiceRequest.builder()
                .productInfoSeq(productInfoSeq)
                .productName(productName)
                .productDetail(productDetail)
                .price(price)
                .productOptions(productOptions)
                .build();
    }
}
