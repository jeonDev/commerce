package com.commerce.core.service.payment;

import com.commerce.core.entity.OrderDetail;
import com.commerce.core.entity.Orders;
import com.commerce.core.entity.Payment;
import com.commerce.core.entity.repository.PaymentRepository;
import com.commerce.core.service.order.OrderService;
import com.commerce.core.vo.common.type.PaymentStatus;
import com.commerce.core.vo.payment.PaymentDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

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
