package com.commerce.core.order;

import com.commerce.core.entity.Orders;
import com.commerce.core.service.order.OrderService;
import com.commerce.core.vo.order.OrderDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

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
}
