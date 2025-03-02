package com.commerce.core.api.response;

import com.commerce.core.point.vo.PointProcessStatus;
import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record PointAdjustmentResponse(
        Long memberSeq,
        Long point,
        Long balancePoint,
        PointProcessStatus pointProcessStatus,
        LocalDateTime createDt
) {
}
