package com.commerce.core.order.service;

import com.commerce.core.member.domain.MemberDao;
import com.commerce.core.order.domain.OrderDao;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class OrderServiceImplTest {
    @Mock
    private OrderDao orderDao;
    @Mock
    private MemberDao memberDao;
    private OrderService orderService;

    @BeforeEach
    void setUp() {
        orderService = new OrderService(orderDao, memberDao);
    }
    // TODO:
}