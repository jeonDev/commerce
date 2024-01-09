package com.commerce.core.payment.service;

import com.commerce.core.order.entity.OrderDetail;
import com.commerce.core.order.entity.Orders;
import com.commerce.core.payment.entity.Payment;
import com.commerce.core.payment.repository.PaymentRepository;
import com.commerce.core.order.service.OrderService;
import com.commerce.core.payment.vo.PaymentStatus;
import com.commerce.core.payment.vo.PaymentDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class PaymentServiceImpl implements PaymentService {

    private final PaymentRepository paymentRepository;

    private final OrderService orderService;

    @Override
    public Payment payment(PaymentDto dto) {
        Long orderSeq = dto.getOrderSeq();
        Orders orders = orderService.selectOrder(orderSeq);
        long paymentAmount = orderService.selectOrderDetailList(orderSeq)
                .stream()
                .mapToLong(OrderDetail::getBuyAmount)
                .sum();

        Payment payment = Payment.builder()
                .orderSeq(orders)
                .paymentStatus(PaymentStatus.of(dto.getPaymentStatus()))
                .initPaymentAmount(paymentAmount)
                .paymentAmount(paymentAmount)
                .refundAmount(0L)
                .build();

        return paymentRepository.save(payment);
    }
}
