package com.commerce.core.order.vo;

import lombok.Getter;
import lombok.Setter;

@Getter
public class PaymentDto {
    @Setter
    private Long memberSeq;
    private Long orderSeq;

}
