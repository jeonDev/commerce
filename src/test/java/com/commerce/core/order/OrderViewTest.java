package com.commerce.core.order;

import com.commerce.core.order.service.OrderViewService;
import com.commerce.core.order.vo.OrderViewDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class OrderViewTest {

    @Autowired
    OrderViewService orderViewService;

    @Test
    void merge() {
        OrderViewDto dto = OrderViewDto.builder()
                .orderSeq(1L)
                .build();
        orderViewService.merge(dto);
    }
}
