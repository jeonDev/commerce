package com.commerce.core.order.vo;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

@Getter
@AllArgsConstructor
public enum OrderStatus {

    NEW_ORDER("0"),            // 신규 주문
    PAYMENT_COMPLETE("1");     // 결제 완료

    private final String status;
}
