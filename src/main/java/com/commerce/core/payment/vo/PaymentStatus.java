package com.commerce.core.payment.vo;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

@Getter
@AllArgsConstructor
public enum PaymentStatus {

    PAYMENT_WAIT("0"),      // 결제 대기
    PAYMENT_COMPLETE("1"),  // 결제 완료
    REFUND_REQUEST("2"),    // 환불 요청
    REFUND_COMPLETE("3");   // 환불 완료

    private final String status;

    public static PaymentStatus of(String status) {
        return Arrays.stream(values())
                .filter(value -> value.status.equals(status))
                .findAny()
                .orElse(null);
    }
}
