package com.commerce.core.vo.common.type;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

@Getter
@AllArgsConstructor
public enum OrderStatus {

    NEW_ORDER("1"),             // 신규 주문
    WAITING_FOR_SHIPMENT("2"),  // 발송 대기
    DELIVERY("3"),              // 배송 중
    DELIVERY_COMPLETE("4"),     // 배송 완료
    PURCHASE_CONFIRM("5"),      // 구매 확정
    REQUEST_CANCEL("6");        // 취소 요청

    private final String status;

    public static OrderStatus of(String status) {
        return Arrays.stream(values())
                .filter(value -> value.status.equals(status))
                .findAny()
                .orElse(null);
    }
}
