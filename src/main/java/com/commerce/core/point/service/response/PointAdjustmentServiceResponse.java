package com.commerce.core.point.service.response;

import com.commerce.core.point.domain.entity.PointHistory;
import com.commerce.core.point.type.PointProcessStatus;
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

    public static PointAdjustmentServiceResponse from(PointHistory pointHistory) {
        return PointAdjustmentServiceResponse.builder()
                .memberSeq(pointHistory.getMemberSeq())
                .point(pointHistory.getPoint())
                .pointProcessStatus(pointHistory.getPointProcessStatus())
                .createDt(pointHistory.getCreateDt())
                .build();
    }
}
