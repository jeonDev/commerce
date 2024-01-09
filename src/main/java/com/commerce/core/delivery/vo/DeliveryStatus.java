package com.commerce.core.delivery.vo;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

@Getter
@AllArgsConstructor
public enum DeliveryStatus {

    DELIVERY_START("0"),        // 배송 시작
    DELIVERY_PROCEEDING("1"),   // 배송 진행 중
    DELIVERY_COMPLETE("2");     // 배송 완료

    private final String status;

    public static DeliveryStatus of(String status) {
        return Arrays.stream(values())
                .filter(value -> value.status.equals(status))
                .findAny()
                .orElse(null);
    }
}
