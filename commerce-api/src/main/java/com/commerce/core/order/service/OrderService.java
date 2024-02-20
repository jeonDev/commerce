package com.commerce.core.order.service;

import com.commerce.core.order.entity.OrderDetail;
import com.commerce.core.order.entity.Orders;
import com.commerce.core.order.vo.OrderDto;

import java.util.List;
import java.util.Optional;

public interface OrderService {
    /**
     * Product Order
     */
    Orders order(OrderDto dto);

    /**
     * Select Orders
     */
    Optional<Orders> selectOrder(Long orderSeq);

    /**
     * Order Status Update
     */
    OrderDetail updateOrderStatus(OrderDto dto);

    /**
     * Select Order Detail
     */
    Optional<OrderDetail> selectOrderDetail(Long orderDetailSeq);

    /**
     * Select Order Detail List
     */
    List<OrderDetail> selectOrderDetailList(Long orderSeq);
}
