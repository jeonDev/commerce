package com.commerce.core.api.request;

import com.commerce.core.common.utils.SessionUtils;
import com.commerce.core.point.service.request.PointAdjustmentServiceRequest;
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

    public PointAdjustmentServiceRequest toRequest() {
        return PointAdjustmentServiceRequest.builder()
                .memberSeq(SessionUtils.getMemberSeq())
                .point(point)
                .balancePoint(balancePoint)
                .pointProcessStatus(pointProcessStatus)
                .createDt(createDt)
                .build();
    }
}
