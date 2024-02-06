package com.commerce.core.point.vo;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;

@Builder
@Getter
public class PointDto {
    private Long memberSeq;
    private Long point;
}
