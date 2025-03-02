package com.commerce.core.point.service.response;

import com.commerce.core.api.response.PointAdjustmentResponse;
import com.commerce.core.point.vo.PointProcessStatus;
import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record PointAdjustmentServiceResponse(
        Long memberSeq,
        Long point,
        Long balancePoint,
        PointProcessStatus pointProcessStatus,
        LocalDateTime createDt
) {

    public PointAdjustmentResponse toResponse() {
        return PointAdjustmentResponse.builder()
                .memberSeq(memberSeq)
                .point(point)
                .balancePoint(balancePoint)
                .pointProcessStatus(pointProcessStatus)
                .createDt(createDt)
                .build();
    }
}
