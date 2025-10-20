package com.commerce.core.api.request;

import io.swagger.v3.oas.annotations.media.Schema;

public record PaymentRequest(
        @Schema(description = "주문번호", example = "1")
        Long orderSeq
) {

}
