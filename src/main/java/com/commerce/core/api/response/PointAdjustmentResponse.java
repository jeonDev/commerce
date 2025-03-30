package com.commerce.core.api.response;

import com.commerce.core.point.service.response.PointAdjustmentServiceResponse;
import com.commerce.core.point.type.PointProcessStatus;
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

    public static PointAdjustmentResponse from(PointAdjustmentServiceResponse response) {
        return PointAdjustmentResponse.builder()
                .memberSeq(response.memberSeq())
                .point(response.point())
                .balancePoint(response.balancePoint())
                .pointProcessStatus(response.pointProcessStatus())
                .createDt(response.createDt())
                .build();
    }
}
