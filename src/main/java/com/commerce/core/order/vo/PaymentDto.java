package com.commerce.core.order.vo;

import lombok.Data;

import java.util.List;

@Data
public class PaymentDto {

    private Long memberSeq;
    private List<PaymentInfoDto> paymentInfos;

}
