package com.commerce.core.order;

import com.commerce.core.order.entity.OrderDetail;
import com.commerce.core.order.entity.Orders;
import com.commerce.core.order.service.OrderService;
import com.commerce.core.order.vo.OrderStatus;
import com.commerce.core.order.vo.OrderDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class OrderTest {

    @Autowired
    OrderService orderService;

    @Test
    void order() {
        Long[] productSeqs = {1L};
        OrderDto orderDto = OrderDto.builder()
                .memberSeq(1L)
                .productSeqs(productSeqs)
                .build();
        Orders order = orderService.order(orderDto);
    }

    @Test
    void updateOrderDetailStatus() {
        String orderStatus = OrderStatus.WAITING_FOR_SHIPMENT.getStatus();
        OrderDto dto = OrderDto.builder()
                .orderDetailSeq(1L)
                .orderStatus(orderStatus)
                .build();
        OrderDetail result = orderService.updateOrderStatus(dto);

        OrderDetail orderDetail = orderService.selectOrderDetail(dto.getOrderDetailSeq()).get();

        assertThat(orderStatus).isEqualTo(orderDetail.getOrderStatus());
    }
}
