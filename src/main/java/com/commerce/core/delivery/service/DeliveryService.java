package com.commerce.core.delivery.service;

import com.commerce.core.delivery.entity.Delivery;
import com.commerce.core.order.entity.OrderDetail;
import com.commerce.core.delivery.vo.DeliveryDto;

public interface DeliveryService {

    /**
     * Delivery History Register
     * @param dto
     * @return
     */
    Delivery registerDeliveryInfo(DeliveryDto dto);

    /**
     * Select MAX History
     */
    Delivery selectDeliveryTopDetail(OrderDetail orderDetail);
}
