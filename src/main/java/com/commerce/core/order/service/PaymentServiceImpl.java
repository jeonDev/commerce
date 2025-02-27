package com.commerce.core.order.service;

import com.commerce.core.common.exception.CommerceException;
import com.commerce.core.common.exception.ExceptionStatus;
import com.commerce.core.event.EventTopic;
import com.commerce.core.event.producer.EventSender;
import com.commerce.core.order.domain.OrderDao;
import com.commerce.core.order.domain.entity.OrderDetail;
import com.commerce.core.order.domain.entity.OrderDetailHistory;
import com.commerce.core.order.domain.entity.Orders;
import com.commerce.core.order.vo.*;
import com.commerce.core.point.service.PointService;
import com.commerce.core.point.vo.PointDto;
import com.commerce.core.point.vo.PointProcessStatus;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class PaymentServiceImpl implements PaymentService {

    private final OrderDao orderDao;
    private final PointService pointService;
    private final EventSender eventSender;

    @Transactional
    @Override
    public Orders payment(PaymentDto dto) {
        // 1. Order Detail Find
        Long orderSeq = dto.getOrderSeq();
        List<OrderDetail> orderDetails = orderDao.orderDetailListByOrderSeq(orderSeq);

        // 2. Payment Amount Calculator
        long payAmount = orderDetails.stream()
                .mapToLong(item -> item.getBuyAmount() - item.getPaidAmount())
                .sum();

        // 2-1. Payment Amount Empty => Exception
        if(payAmount <= 0) {
            throw new CommerceException(ExceptionStatus.PAYMENT_AMOUNT_ERROR);
        }

        // 3. Payment (Point Withdraw)
        PointDto pointDto = PointDto.builder()
                .memberSeq(dto.getMemberSeq())
                .point(payAmount)
                .pointProcessStatus(PointProcessStatus.PAYMENT)
                .build();
        pointService.pointAdjustment(pointDto);

        // 4. Payment Success Save
        orderDetails.forEach(this::paymentAmountBeforeProcess);
        orderDao.orderDetailSaveAll(orderDetails);

        // 5. Event Send(Order View Mongo DB)
        OrderViewDto orderViewDto = OrderViewDto.builder()
                .orderSeq(orderSeq)
                .build();
        eventSender.send(EventTopic.SYNC_ORDER, orderViewDto);

        return null;
    }

    private void paymentAmountBeforeProcess(OrderDetail item) {
        item.paymentSuccessSettingPaidAmount(item.getBuyAmount());
        OrderDto orderDto = OrderDto.builder()
                .orderDetailSeq(item.getOrderDetailSeq())
                .orderStatus(OrderStatus.PAYMENT_COMPLETE)
                .build();
        this.updateOrderStatus(orderDto);

        orderDao.paymentHistorySave(item.generateHistoryEntity(item.getPaidAmount(), InoutDivisionStatus.PAYMENT));
    }

    private OrderDetail updateOrderStatus(OrderDto dto) {
        OrderDetail orderDetail = orderDao.orderDetailFindById(dto.getOrderDetailSeq())
                .orElseThrow(() -> new CommerceException(ExceptionStatus.ENTITY_IS_EMPTY));

        orderDetail.updateOrderStatus(dto.getOrderStatus());
        orderDetail = orderDao.orderDetailSave(orderDetail);

        OrderDetailHistory orderDetailHistory = orderDetail.generateHistoryEntity();
        orderDao.orderDetailHistorySave(orderDetailHistory);

        return orderDetail;
    }
}
