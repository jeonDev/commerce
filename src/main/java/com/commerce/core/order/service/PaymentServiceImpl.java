package com.commerce.core.order.service;

import com.commerce.core.order.entity.OrderDetail;
import com.commerce.core.order.entity.Orders;
import com.commerce.core.order.repository.OrderDetailsRepository;
import com.commerce.core.order.vo.OrderDto;
import com.commerce.core.order.vo.OrderStatus;
import com.commerce.core.order.vo.PaymentDto;
import com.commerce.core.point.service.PointService;
import com.commerce.core.point.vo.PointDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class PaymentServiceImpl implements PaymentService {

    private final OrderDetailsRepository orderDetailsRepository;

    private final OrderService orderService;
    private final PointService pointService;

    @Transactional
    @Override
    public Orders payment(PaymentDto dto) {

        // 1. 결제 대상 확인
        Long orderSeq = dto.getOrderSeq();
        List<OrderDetail> orderDetails = orderService.selectOrderDetailList(orderSeq);

        // 1-1. 결제 금액 계산
        long payAmount = orderDetails.stream()
                .peek(item -> {
                    item.paymentSuccessSettingPaidAmount();
                    OrderDto orderDto = OrderDto.builder()
                            .orderDetailSeq(item.getOrderDetailSeq())
                            .orderStatus(OrderStatus.PAYMENT_COMPLETE.getStatus())
                            .build();
                    orderService.updateOrderStatus(orderDto);
                })
                .mapToLong(OrderDetail::getBuyAmount)
                .sum();

        // 2. 결제 처리
        PointDto pointDto = PointDto.builder()
                .memberSeq(dto.getMemberSeq())
                .point(payAmount)
                .build();
        pointService.withdraw(pointDto);

        // 3. DB 후 처리
        orderDetailsRepository.saveAll(orderDetails);

        return null;
    }
}
