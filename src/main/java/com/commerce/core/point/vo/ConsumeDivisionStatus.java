package com.commerce.core.point.vo;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

@Getter
@AllArgsConstructor
public enum ConsumeDivisionStatus {

    CHARGE("0"),        // 충전
    PAYMENT("1");       // 결제

    private final String status;
}
