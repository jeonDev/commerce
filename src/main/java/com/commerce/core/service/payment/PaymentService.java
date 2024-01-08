package com.commerce.core.service.payment;

import com.commerce.core.entity.Payment;
import com.commerce.core.vo.payment.PaymentDto;

public interface PaymentService {
    /**
     * 결제 후 처리
     * @param dto
     * @return
     */
    Payment payment(PaymentDto dto);
}
