package com.commerce.core.api.response;

import com.commerce.core.point.domain.entity.MemberPoint;
import lombok.Builder;

@Builder
public record PointAdjustmentResponse(
        Long memberSeq,
        Long balancePoint
) {

    public static PointAdjustmentResponse from(MemberPoint memberPoint) {
        return PointAdjustmentResponse.builder()
                .memberSeq(memberPoint.getMember().getMemberSeq())
                .balancePoint(memberPoint.getPoint())
                .build();
    }
}
