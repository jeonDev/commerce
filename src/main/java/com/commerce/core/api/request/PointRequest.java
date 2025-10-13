package com.commerce.core.api.request;

import com.commerce.core.point.type.PointProcessStatus;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record PointRequest(
        @Min(value = 0, message = "금액을 확인해주세요.")
        @NotNull(message = "금액을 확인해주세요.")
        Long point,
        Long balancePoint,
        @NotNull
        PointProcessStatus pointProcessStatus,
        LocalDateTime createDt
) {
}
