package com.commerce.core.vo.common.type;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

@Getter
@AllArgsConstructor
public enum DeliveryStatus {

    DELIVERY_START("1"),        // 배송 시작
    DELIVERY_PROCEEDING("2"),   // 배송 진행 중
    DELIVERY_COMPLETE("3");     // 배송 완료

    private final String status;

    public static DeliveryStatus of(String status) {
        return Arrays.stream(values())
                .filter(value -> value.status.equals(status))
                .findAny()
                .orElse(null);
    }
}
