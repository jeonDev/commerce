package com.commerce.core.point.vo;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class PointDto {
    private Long memberSeq;
    private BigDecimal point;
}
