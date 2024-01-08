package com.commerce.core.payment;

import com.commerce.core.entity.Payment;
import com.commerce.core.service.payment.PaymentService;
import com.commerce.core.vo.payment.PaymentDto;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@Slf4j
@SpringBootTest
public class PaymentTest {

    @Autowired
    PaymentService paymentService;

    @Test
    void payment() {
        PaymentDto dto = new PaymentDto();
        dto.setOrderSeq(2L);
        dto.setPaymentStatus("0");
        Payment payment = paymentService.payment(dto);
    }
}
