package com.commerce.core.order.type;

import io.swagger.v3.oas.annotations.media.Schema;

public record BuyProduct(
        @Schema(description = "상품 번호", example = "1") Long productSeq,
        @Schema(description = "개수", example = "2") Long cnt
) {
}
