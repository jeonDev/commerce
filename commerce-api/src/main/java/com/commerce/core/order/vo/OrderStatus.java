package com.commerce.core.order.vo;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

@Getter
@AllArgsConstructor
public enum OrderStatus {

    NEW_ORDER("0"),            // 신규 주문
    PAYMENT_COMPLETE("1");     // 결제 완료

    private final String status;

    @JsonCreator
    public static OrderStatus from(String value) {
        return Arrays.stream(values())
                .filter(v -> v.status.equals(value))
                .findAny()
                .orElseThrow();
    }
}
