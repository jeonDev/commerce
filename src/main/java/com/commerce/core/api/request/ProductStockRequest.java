package com.commerce.core.api.request;

import com.commerce.core.product.domain.type.ProductStockProcessStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record ProductStockRequest(
        @Schema(description = "상품번호", example = "1") @NotNull Long productSeq,
        @Schema(description = "재고", example = "10") @Min(0) @NotNull Long stock,
        @Schema(description = "재고 추가 차감 여부", example = "0") @NotNull ProductStockProcessStatus productStockProcessStatus
) {
}
