package com.commerce.core.order.service;

import com.commerce.core.common.exception.CommerceException;
import com.commerce.core.common.exception.ExceptionStatus;
import com.commerce.core.event.EventTopic;
import com.commerce.core.event.producer.EventSender;
import com.commerce.core.order.domain.OrderDao;
import com.commerce.core.order.domain.entity.OrderDetail;
import com.commerce.core.order.domain.entity.OrderDetailHistory;
import com.commerce.core.order.domain.entity.Orders;
import com.commerce.core.order.domain.entity.PaymentHistory;
import com.commerce.core.order.service.request.OrderServiceRequest;
import com.commerce.core.order.service.request.OrderViewMergeServiceRequest;
import com.commerce.core.order.service.request.PaymentServiceRequest;
import com.commerce.core.order.vo.*;
import com.commerce.core.point.service.PointService;
import com.commerce.core.point.service.request.PointAdjustmentServiceRequest;
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
    public Orders payment(PaymentServiceRequest request) {
        // 1. Order Detail Find
        Long orderSeq = request.orderSeq();
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
        PointAdjustmentServiceRequest pointAdjustmentRequest = PointAdjustmentServiceRequest.builder()
                .memberSeq(request.memberSeq())
                .point(payAmount)
                .pointProcessStatus(PointProcessStatus.PAYMENT)
                .build();
        pointService.pointAdjustment(pointAdjustmentRequest);

        // 4. Payment Success Save
        orderDetails.forEach(this::paymentAmountBeforeProcess);
        orderDao.orderDetailSaveAll(orderDetails);

        // 5. Event Send(Order View Mongo DB)
        OrderViewMergeServiceRequest orderEventRequest = OrderViewMergeServiceRequest.builder()
                .orderSeq(orderSeq)
                .build();
        eventSender.send(EventTopic.SYNC_ORDER, orderEventRequest);

        return null;
    }

    private void paymentAmountBeforeProcess(OrderDetail item) {
        item.paymentSuccessSettingPaidAmount(item.getBuyAmount());
        OrderServiceRequest orderRequest = OrderServiceRequest.builder()
                .orderDetailSeq(item.getOrderDetailSeq())
                .orderStatus(OrderStatus.PAYMENT_COMPLETE)
                .build();
        this.updateOrderStatus(orderRequest);

        orderDao.paymentHistorySave(PaymentHistory.from(item, item.getPaidAmount(), InoutDivisionStatus.PAYMENT));
    }

    private OrderDetail updateOrderStatus(OrderServiceRequest request) {
        OrderDetail orderDetail = orderDao.orderDetailFindById(request.orderDetailSeq())
                .orElseThrow(() -> new CommerceException(ExceptionStatus.ENTITY_IS_EMPTY));

        orderDetail.updateOrderStatus(request.orderStatus());
        orderDetail = orderDao.orderDetailSave(orderDetail);

        orderDao.orderDetailHistorySave(OrderDetailHistory.from(orderDetail));

        return orderDetail;
    }
}
