package com.commerce.core.delivery.vo;

import com.commerce.core.delivery.entity.Delivery;
import com.commerce.core.order.entity.OrderDetail;
import lombok.Data;

@Data
public class DeliveryDto {

    private Long orderDetailSeq;
    private String deliveryStatus;

    public Delivery dtoToEntity(OrderDetail orderDetail) {
        return Delivery.builder()
                .orderDetail(orderDetail)
                .deliveryStatus(DeliveryStatus.of(deliveryStatus))
                .build();
    }
}
