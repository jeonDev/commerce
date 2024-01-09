package com.commerce.core.payment.service;

import com.commerce.core.payment.entity.Payment;
import com.commerce.core.payment.vo.PaymentDto;

public interface PaymentService {
    /**
     * 결제 후 처리
     * @param dto
     * @return
     */
    Payment payment(PaymentDto dto);
}
