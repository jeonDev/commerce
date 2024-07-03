package com.commerce.core.order;

import com.commerce.core.order.service.PaymentService;
import com.commerce.core.order.vo.PaymentDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
public class PaymentTest {

    @Autowired
    PaymentService paymentService;

    @Test
    @Transactional
    void payment_success() {
        PaymentDto dto = new PaymentDto();
        dto.setMemberSeq(1L);
        dto.setOrderSeq(2L);
        paymentService.payment(dto);
    }

    @Test
    @Transactional
    void payment_fail() {

    }
}
