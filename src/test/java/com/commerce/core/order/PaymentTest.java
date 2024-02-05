package com.commerce.core.order;

import com.commerce.core.order.service.PaymentService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class PaymentTest {

    @Autowired
    PaymentService paymentService;

    @Test
    void payment_success() {

    }

    @Test
    void payment_fail() {

    }
}
