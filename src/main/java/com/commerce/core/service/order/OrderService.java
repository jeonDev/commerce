package com.commerce.core.service.order;

import com.commerce.core.entity.Orders;
import com.commerce.core.vo.order.OrderDto;

public interface OrderService {
    /**
     * Product Order
     * @param dto
     * @return
     */
    Orders order(OrderDto dto);
}
