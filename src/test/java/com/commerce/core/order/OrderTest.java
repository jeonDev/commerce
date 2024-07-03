package com.commerce.core.order;

import com.commerce.core.order.entity.OrderDetail;
import com.commerce.core.order.entity.Orders;
import com.commerce.core.order.service.OrderService;
import com.commerce.core.order.vo.OrderStatus;
import com.commerce.core.order.vo.OrderDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class OrderTest {

    @Autowired
    OrderService orderService;

    @Test
    @Transactional
    void order() {
        Long[] productSeqs = {1L, 2L};
        OrderDto orderDto = OrderDto.builder()
                .memberSeq(1L)
                .productSeqs(productSeqs)
                .build();
        Orders order = orderService.order(orderDto);
    }

    @Test
    @Transactional
    void updateOrderDetailStatus() {
        OrderDto dto = OrderDto.builder()
                .orderDetailSeq(1L)
                .orderStatus(OrderStatus.PAYMENT_COMPLETE)
                .build();
        orderService.updateOrderStatus(dto);
        OrderDetail orderDetail = orderService.selectOrderDetail(dto.getOrderDetailSeq()).get();

        assertThat(OrderStatus.PAYMENT_COMPLETE).isEqualTo(orderDetail.getOrderStatus());
    }
}
