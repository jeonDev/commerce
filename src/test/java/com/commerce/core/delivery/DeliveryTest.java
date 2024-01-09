package com.commerce.core.delivery;

import com.commerce.core.delivery.entity.Delivery;
import com.commerce.core.order.entity.OrderDetail;
import com.commerce.core.delivery.service.DeliveryService;
import com.commerce.core.order.service.OrderService;
import com.commerce.core.delivery.vo.DeliveryStatus;
import com.commerce.core.delivery.vo.DeliveryDto;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
@SpringBootTest
public class DeliveryTest {

    @Autowired
    DeliveryService deliveryService;

    @Autowired
    OrderService orderService;

    @Test
    void deliveryStatusSave() {
        DeliveryDto dto = new DeliveryDto();
        dto.setOrderDetailSeq(1L);
        dto.setDeliveryStatus("3");
        Delivery delivery = deliveryService.registerDeliveryInfo(dto);

        OrderDetail orderDetail = orderService.selectOrderDetail(dto.getOrderDetailSeq());
        Delivery maxDelivery = deliveryService.selectDeliveryTopDetail(orderDetail);

        assertThat(delivery.getDeliveryStatus().getStatus()).isEqualTo(DeliveryStatus.DELIVERY_COMPLETE.getStatus());
        assertThat(delivery.getDeliveryStatus().getStatus()).isEqualTo(maxDelivery.getDeliveryStatus().getStatus());
    }
}
