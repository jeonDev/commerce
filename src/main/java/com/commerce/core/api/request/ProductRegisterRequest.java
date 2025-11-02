package com.commerce.core.api.request;

import com.commerce.core.product.facade.request.ProductAddRequest;
import com.commerce.core.product.facade.request.ProductAddRequest.ProductOptionRequest;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record ProductRegisterRequest(
        @Schema(description = "브랜드", example = "1") Long brandSeq,
        @Schema(description = "카테고리", example = "1") Long categorySeq,
        @Schema(description = "상품이름", example = "상푸움") @NotNull String productName,
        @Schema(description = "상품 상세 설명", example = "디스크립션") @NotNull String productDescription,
        @Schema(description = "상품 가격", example = "10000") @Min(0) @NotNull Long price,
        @Schema(description = "재고", example = "100") @Min(0) @NotNull Long stock,
        @Schema(description = "상품 옵션") @NotNull List<ProductRegisterOptionRequest> productOptions
) {
    public record ProductRegisterOptionRequest (
        @Schema(description = "상품 옵션 이름", example = "M") @NotNull String productOptionName,
        @Schema(description = "상품 옵션 설명", example = "Medium") @NotNull String productOptionDescription
    ) {
        public ProductOptionRequest toOptionsRequest() {
            return ProductOptionRequest.builder()
                    .productOptionName(productOptionName)
                    .productOptionDescription(productOptionDescription)
                    .build();
        }
    }

    public ProductAddRequest toRequest() {
        return ProductAddRequest.builder()
                .brandSeq(brandSeq)
                .categorySeq(categorySeq)
                .productName(productName)
                .productDescription(productDescription)
                .price(price)
                .stock(stock)
                .productOptionRequestList(productOptions.stream()
                        .map(ProductRegisterOptionRequest::toOptionsRequest)
                        .toList())
                .build();
    }
}
