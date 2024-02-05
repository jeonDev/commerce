package com.commerce.core.api;

import com.commerce.core.order.service.OrderService;
import com.commerce.core.order.service.PaymentService;
import com.commerce.core.order.vo.OrderDto;
import com.commerce.core.order.vo.PaymentDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/v1/order")
@RestController
public class OrderController {

    private final OrderService orderService;
    private final PaymentService paymentService;

    @PostMapping("/order")
    public void order(@RequestBody OrderDto dto) {
        orderService.order(dto);
    }

    @PostMapping("/payment")
    public void pointCharge(@RequestBody PaymentDto dto) {
        paymentService.payment(dto);
    }

}
