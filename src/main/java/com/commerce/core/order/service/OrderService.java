package com.commerce.core.order.service;

import com.commerce.core.order.entity.OrderDetail;
import com.commerce.core.order.entity.Orders;
import com.commerce.core.order.vo.OrderDto;

import java.util.List;

public interface OrderService {
    /**
     * Product Order
     * @param dto
     * @return
     */
    Orders order(OrderDto dto);

    /**
     * Select Orders
     * @param orderSeq
     * @return
     */
    Orders selectOrder(Long orderSeq);

    /**
     * Order Status Update
     */
    OrderDetail updateOrderStatus(OrderDto dto);

    /**
     * Select Order Detail
     */
    OrderDetail selectOrderDetail(Long orderDetailSeq);

    /**
     * Select Order Detail List
     * @param orderSeq
     * @return
     */
    List<OrderDetail> selectOrderDetailList(Long orderSeq);
}
