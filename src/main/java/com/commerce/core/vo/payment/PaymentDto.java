package com.commerce.core.vo.payment;

import lombok.Data;

@Data
public class PaymentDto {
    private Long paymentSeq;
    private Long orderSeq;
    private String paymentStatus;

}
