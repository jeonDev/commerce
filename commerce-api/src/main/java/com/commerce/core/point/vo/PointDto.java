package com.commerce.core.point.vo;

import lombok.*;

@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
public class PointDto {
    private Long memberSeq;
    private Long point;
    private Long balancePoint;
    private PointProcessStatus pointProcessStatus;
}
