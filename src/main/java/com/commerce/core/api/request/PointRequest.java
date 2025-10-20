package com.commerce.core.api.request;

import com.commerce.core.point.type.PointProcessStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record PointRequest(
        @Min(value = 0, message = "금액을 확인해주세요.")
        @NotNull(message = "금액을 확인해주세요.")
        @Schema(description = "포인트", example = "10000")
        Long point,
        @NotNull
        @Schema(description = "충전차감 구분", example = "0")
        PointProcessStatus pointProcessStatus
) {
}
