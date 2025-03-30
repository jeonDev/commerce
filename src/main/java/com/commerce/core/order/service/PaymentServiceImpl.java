package com.commerce.core.order.service;

import com.commerce.core.common.exception.CommerceException;
import com.commerce.core.common.exception.ExceptionStatus;
import com.commerce.core.event.EventTopic;
import com.commerce.core.event.producer.EventSender;
import com.commerce.core.order.domain.OrderDao;
import com.commerce.core.order.domain.entity.OrderDetail;
import com.commerce.core.order.domain.entity.OrderDetailHistory;
import com.commerce.core.order.domain.entity.PaymentHistory;
import com.commerce.core.order.service.request.OrderViewMergeServiceRequest;
import com.commerce.core.order.service.request.PaymentServiceRequest;
import com.commerce.core.order.type.InoutDivisionStatus;
import com.commerce.core.point.service.PointService;
import com.commerce.core.point.service.request.PointAdjustmentServiceRequest;
import com.commerce.core.point.type.PointProcessStatus;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
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
    public boolean payment(PaymentServiceRequest request) {
        log.info("[Payment] 결제 요청 | 고객 번호 : {} | 주문 번호 : {}", request.memberSeq(), request.orderSeq());
        // 1. Order Detail Find
        Long orderSeq = request.orderSeq();
        List<OrderDetail> orderDetails = orderDao.orderDetailListByOrderSeq(orderSeq);

        // 2. Payment Amount Calculator
        long payAmount = orderDetails.stream()
                .mapToLong(item -> item.getBuyAmount() - item.getPaidAmount())
                .sum();

        if (payAmount <= 0) {
            throw new CommerceException(ExceptionStatus.PAYMENT_AMOUNT_ERROR);
        }

        // 3. Payment (Point Withdraw)
        this.pay(request.memberSeq(), payAmount);

        // 4. Payment Success Save
        this.paymentSuccessHistorySave(orderDetails);

        // 5. Event Send(Order View Mongo DB)
        OrderViewMergeServiceRequest orderEventRequest = OrderViewMergeServiceRequest.builder()
                .orderSeq(orderSeq)
                .build();
        eventSender.send(EventTopic.SYNC_ORDER, orderEventRequest);

        return true;
    }

    public void pay(Long memberSeq, Long payAmount) {
        PointAdjustmentServiceRequest pointAdjustmentRequest = PointAdjustmentServiceRequest.builder()
                .memberSeq(memberSeq)
                .point(payAmount)
                .pointProcessStatus(PointProcessStatus.PAYMENT)
                .build();
        pointService.pointAdjustment(pointAdjustmentRequest);
    }

    public void paymentSuccessHistorySave(List<OrderDetail> list) {
        List<OrderDetailHistory> orderDetailHistoryList = new ArrayList<>();
        List<PaymentHistory> paymentHistoryList = new ArrayList<>();
        List<OrderDetail> orderDetailList = list.stream()
                .peek(item -> {
                    item.paymentSuccessSettingPaidAmount(item.getBuyAmount());
                    item.paymentComplete();
                    orderDetailHistoryList.add(OrderDetailHistory.from(item));
                    paymentHistoryList.add(PaymentHistory.from(item, item.getPaidAmount(), InoutDivisionStatus.PAYMENT));
                })
                .toList();
        orderDao.orderDetailSaveAll(orderDetailList);
        orderDao.orderDetailHistorySaveAll(orderDetailHistoryList);
        orderDao.paymentHistorySaveAll(paymentHistoryList);
    }
}
