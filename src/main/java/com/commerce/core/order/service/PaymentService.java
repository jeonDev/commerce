package com.commerce.core.order.service;

import com.commerce.core.common.exception.CommerceException;
import com.commerce.core.common.exception.ExceptionStatus;
import com.commerce.core.order.domain.OrderDao;
import com.commerce.core.order.domain.entity.OrderDetail;
import com.commerce.core.order.domain.entity.OrderDetailHistory;
import com.commerce.core.order.domain.entity.PaymentHistory;
import com.commerce.core.order.type.InoutDivisionStatus;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class PaymentService {

    private final OrderDao orderDao;

    public PaymentService(OrderDao orderDao) {
        this.orderDao = orderDao;
    }

    @Transactional(readOnly = true)
    public List<OrderDetail> getOrderDetailList(Long orderSeq) {
        return orderDao.orderDetailListByOrderSeq(orderSeq);
    }

    public Long getPaymentAmount(List<OrderDetail> orderDetails) {
        long payAmount = orderDetails.stream()
                .mapToLong(OrderDetail::getPaymentAmount)
                .sum();

        if (payAmount <= 0) {
            throw new CommerceException(ExceptionStatus.PAYMENT_AMOUNT_ERROR);
        }

        return payAmount;
    }

    @Transactional
    public void paymentSuccessHistorySave(List<OrderDetail> list) {
        var orderDetailHistoryList = new ArrayList<OrderDetailHistory>();
        var paymentHistoryList = new ArrayList<PaymentHistory>();
        var orderDetailList = list.stream()
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
