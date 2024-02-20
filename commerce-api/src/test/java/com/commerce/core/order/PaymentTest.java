package com.commerce.core.order;

import com.commerce.core.order.service.PaymentService;
import com.commerce.core.order.vo.PaymentDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class PaymentTest {

    @Autowired
    PaymentService paymentService;

    @Test
    void payment_success() {
        PaymentDto dto = new PaymentDto();
        dto.setMemberSeq(1L);
        dto.setOrderSeq(2L);
        paymentService.payment(dto);
    }

    @Test
    void payment_fail() {

    }
}
