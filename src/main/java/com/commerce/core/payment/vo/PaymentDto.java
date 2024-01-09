package com.commerce.core.payment.vo;

import lombok.Data;

@Data
public class PaymentDto {
    private Long paymentSeq;
    private Long orderSeq;
    private String paymentStatus;

}
