package com.commerce.core.order;

import com.commerce.core.entity.OrderDetail;
import com.commerce.core.entity.Orders;
import com.commerce.core.service.order.OrderService;
import com.commerce.core.vo.common.type.OrderStatus;
import com.commerce.core.vo.order.OrderDto;
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
        OrderDto orderDto = new OrderDto();
        orderDto.setMemberSeq(1L);
        Long[] productSeqs = {1L};
        orderDto.setProductSeqs(productSeqs);
        Orders order = orderService.order(orderDto);
    }

    @Test
    void updateOrderDetailStatus() {
        OrderDto dto = new OrderDto();
        OrderStatus orderStatus = OrderStatus.WAITING_FOR_SHIPMENT;
        dto.setOrderDetailSeq(1L);
        dto.setOrderStatus(orderStatus.getStatus());
        OrderDetail result = orderService.updateOrderStatus(dto);

        OrderDetail orderDetail = orderService.selectOrderDetail(dto.getOrderDetailSeq());

        assertThat(orderStatus).isEqualTo(orderDetail.getOrderStatus());
    }
}
