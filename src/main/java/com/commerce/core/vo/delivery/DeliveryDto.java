package com.commerce.core.vo.delivery;

import com.commerce.core.entity.Delivery;
import com.commerce.core.entity.OrderDetail;
import com.commerce.core.vo.common.type.DeliveryStatus;
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
