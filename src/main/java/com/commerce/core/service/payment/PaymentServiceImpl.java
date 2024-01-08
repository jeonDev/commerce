package com.commerce.core.service.payment;

import com.commerce.core.entity.Payment;
import com.commerce.core.vo.payment.PaymentDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class PaymentServiceImpl implements PaymentService {

    @Override
    public Payment payment(PaymentDto dto) {
        return null;
    }
}
