package com.commerce.core.point.service.request;

import com.commerce.core.point.vo.PointProcessStatus;
import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record PointAdjustmentServiceRequest(
        Long memberSeq,
        Long point,
        Long balancePoint,
        PointProcessStatus pointProcessStatus,
        LocalDateTime createDt
) {

}
