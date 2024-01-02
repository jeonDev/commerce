package com.commerce.core.vo.common.type;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum DeliveryStatus {

    DELIVERY_START("1"),        // 배송 시작
    DELIVERY_PROCEEDING("2"),   // 배송 진행 중
    DELIVERY_COMPLETE("3");     // 배송 완료

    private final String status;
}
