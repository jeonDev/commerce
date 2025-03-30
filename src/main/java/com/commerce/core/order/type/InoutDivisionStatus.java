package com.commerce.core.order.type;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum InoutDivisionStatus {
    CHARGE("0"),        // 충전
    PAYMENT("1");       // 결제

    private final String status;
}
