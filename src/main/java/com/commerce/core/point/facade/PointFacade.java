package com.commerce.core.point.facade;

import com.commerce.core.api.response.PointAdjustmentResponse;
import com.commerce.core.point.domain.entity.MemberPoint;
import com.commerce.core.point.service.PointService;
import com.commerce.core.point.type.PointProcessStatus;
import org.springframework.stereotype.Component;

@Component
public class PointFacade {
    private final PointService pointService;

    public PointFacade(PointService pointService) {
        this.pointService = pointService;
    }

    public PointAdjustmentResponse adjustment(Long memberSeq,
                                              Long point,
                                              PointProcessStatus pointProcessStatus) {
        MemberPoint memberPoint = pointService.pointAdjustment(memberSeq, point, pointProcessStatus);

        return PointAdjustmentResponse.from(memberPoint);
    }
}
