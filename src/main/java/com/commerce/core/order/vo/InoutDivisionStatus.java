package com.commerce.core.order.vo;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

@Getter
@AllArgsConstructor
public enum InoutDivisionStatus {
    CHARGE("0"),        // 충전
    PAYMENT("1");       // 결제

    private final String status;
}
