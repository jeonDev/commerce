package com.commerce.core.order.service;

import com.commerce.core.event.producer.EventSender;
import com.commerce.core.member.entity.Member;
import com.commerce.core.member.service.MemberService;
import com.commerce.core.order.entity.Orders;
import com.commerce.core.order.repository.OrderDetailHistoryRepository;
import com.commerce.core.order.repository.OrderDetailsRepository;
import com.commerce.core.order.repository.OrdersRepository;
import com.commerce.core.order.vo.BuyProduct;
import com.commerce.core.order.vo.OrderDto;
import com.commerce.core.product.entity.Product;
import com.commerce.core.product.entity.ProductInfo;
import com.commerce.core.product.entity.ProductStock;
import com.commerce.core.product.service.ProductStockService;
import com.commerce.core.product.vo.ProductStockDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class OrderServiceImplTest {
    @Mock
    private OrdersRepository ordersRepository;
    @Mock
    private OrderDetailsRepository orderDetailsRepository;
    @Mock
    private OrderDetailHistoryRepository orderDetailHistoryRepository;
    @Mock
    private ProductStockService productStockService;
    @Mock
    private MemberService memberService;
    @Mock
    private EventSender eventSender;
    private OrderService orderService;

    @BeforeEach
    void setUp() {
        orderService = new OrderServiceImpl(ordersRepository,
                orderDetailsRepository, orderDetailHistoryRepository, productStockService, memberService, eventSender);
    }

    @Test
    void order() {
        BuyProduct[] buyProduct = {new BuyProduct(1L, 2L), new BuyProduct(2L, 1L)};
        OrderDto dto = OrderDto.builder()
                .buyProducts(buyProduct)
                .memberSeq(1L)
                .build();
        Mockito.when(ordersRepository.save(Mockito.any(Orders.class)))
                        .thenReturn(Orders.builder()
                                .orderSeq(1L)
                                .build());
        Mockito.when(memberService.selectUseMember(Mockito.anyLong()))
                .thenReturn(Optional.ofNullable(Member.builder()
                        .memberSeq(1L)
                        .build()));
        Mockito.when(productStockService.productStockAdjustment(Mockito.any(ProductStockDto.class)))
                .thenReturn(ProductStock.builder()
                        .product(Product.builder()
                                .productSeq(1L)
                                .productInfo(ProductInfo.builder()
                                        .price(10000L)
                                        .build())
                                .build())
                        .stock(3L)
                        .build());

        Orders order = orderService.order(dto);
        assertEquals(order.getOrderSeq(), 1L);
    }

    @Test
    void selectOrder() {
    }

    @Test
    void updateOrderStatus() {
    }

    @Test
    void selectOrderDetail() {
    }

    @Test
    void selectOrderDetailList() {
    }
}