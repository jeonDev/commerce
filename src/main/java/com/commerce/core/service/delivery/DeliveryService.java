package com.commerce.core.service.delivery;

import com.commerce.core.entity.Delivery;
import com.commerce.core.entity.OrderDetail;
import com.commerce.core.vo.delivery.DeliveryDto;

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
