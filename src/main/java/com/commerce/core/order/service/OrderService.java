package com.commerce.core.order.service;

import com.commerce.core.order.domain.entity.OrderDetail;
import com.commerce.core.order.domain.entity.Orders;
import com.commerce.core.order.service.request.OrderServiceRequest;

import java.util.List;
import java.util.Optional;

public interface OrderService {
    /**
     * Product Order
     */
    Orders order(OrderServiceRequest request);

    /**
     * Select Orders
     */
    Optional<Orders> selectOrder(Long orderSeq);

    /**
     * Select Order Detail
     */
    Optional<OrderDetail> selectOrderDetail(Long orderDetailSeq);

    /**
     * Select Order Detail List
     */
    List<OrderDetail> selectOrderDetailList(Long orderSeq);
}
