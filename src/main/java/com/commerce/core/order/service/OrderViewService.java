package com.commerce.core.order.service;

import com.commerce.core.order.domain.OrderDao;
import com.commerce.core.order.domain.entity.OrderDetail;
import com.commerce.core.order.domain.entity.mongo.OrderView;
import com.commerce.core.order.type.OrderDetailInfo;
import com.commerce.core.order.type.OrderStatus;
import com.commerce.core.order.service.request.OrderViewMergeServiceRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;

@Slf4j
@Service
public class OrderViewService {

    private final OrderDao orderDao;

    public OrderViewService(OrderDao orderDao) {
        this.orderDao = orderDao;
    }

    @Transactional
    public void merge(OrderViewMergeServiceRequest request) {
        log.debug(request.toString());
        // 1. Data Setting
        Long buyAmount = 0L;
        Long amount = 0L;
        Long paidAmount = 0L;
        boolean isPaymentComplete = false;
        var orderDetailInfos = new ArrayList<OrderDetailInfo>();

        // 2. Order Detail Find
        Long orderSeq = request.orderSeq();
        var orderDetails = orderDao.orderDetailListByOrderSeq(orderSeq);

        // 3. Amount Data Setting
        for (OrderDetail orderDetail : orderDetails) {
            buyAmount += orderDetail.getBuyAmount();
            amount += orderDetail.getAmount();
            paidAmount += orderDetail.getPaidAmount();
            orderDetailInfos.add(orderDetail.entityToInfoDto());
            if (OrderStatus.PAYMENT_COMPLETE == orderDetail.getOrderStatus()) isPaymentComplete = true;
        }

        var orderStatus = isPaymentComplete ? OrderStatus.PAYMENT_COMPLETE : OrderStatus.NEW_ORDER;

        // 4. Order View Data Save
        OrderView orderView;
        var optionalOrderView = orderDao.orderViewFindByOrderSeq(orderSeq);
        if (optionalOrderView.isPresent()) {
            orderView = optionalOrderView.get();
            orderView.settingData(amount, buyAmount, paidAmount, orderDetailInfos, orderStatus);
        } else {
            orderView = OrderView.builder()
                    .orderSeq(orderSeq)
                    .orderDetailInfos(orderDetailInfos)
                    .amount(amount)
                    .buyAmount(buyAmount)
                    .paidAmount(paidAmount)
                    .orderStatus(orderStatus)
                    .build();
        }

        orderDao.orderViewSave(orderView);
    }

}
