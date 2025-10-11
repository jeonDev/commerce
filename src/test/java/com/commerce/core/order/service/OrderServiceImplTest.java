package com.commerce.core.order.service;

import com.commerce.core.event.producer.EventSender;
import com.commerce.core.member.domain.MemberDao;
import com.commerce.core.member.domain.entity.Member;
import com.commerce.core.member.service.MemberService;
import com.commerce.core.order.domain.OrderDao;
import com.commerce.core.order.domain.entity.Orders;
import com.commerce.core.order.type.BuyProduct;
import com.commerce.core.order.service.request.OrderServiceRequest;
import com.commerce.core.product.domain.entity.Product;
import com.commerce.core.product.domain.entity.ProductInfo;
import com.commerce.core.product.domain.entity.ProductStock;
import com.commerce.core.product.service.ProductStockService;
import com.commerce.core.product.service.request.ProductStockServiceRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class OrderServiceImplTest {
    @Mock
    private OrderDao orderDao;
    @Mock
    private MemberDao memberDao;
    @Mock
    private ProductStockService productStockService;
    @Mock
    private MemberService memberService;
    @Mock
    private EventSender eventSender;
    @Mock
    private PaymentService paymentService;
    private OrderService orderService;

    @BeforeEach
    void setUp() {
        orderService = new OrderService(orderDao,
                memberDao, productStockService, eventSender, paymentService);
    }

    @Test
    void order() {
        BuyProduct[] buyProduct = {new BuyProduct(1L, 2L), new BuyProduct(2L, 1L)};
        OrderServiceRequest request = OrderServiceRequest.builder()
                .buyProducts(buyProduct)
                .memberSeq(1L)
                .build();
        Mockito.when(orderDao.save(Mockito.any(Orders.class)))
                        .thenReturn(Orders.builder()
                                .orderSeq(1L)
                                .build());
        Mockito.when(memberDao.findByUsingMemberSeq(Mockito.anyLong()))
                .thenReturn(Optional.ofNullable(Member.builder()
                        .memberSeq(1L)
                        .build()));
        Mockito.when(productStockService.productStockAdjustment(Mockito.any(ProductStockServiceRequest.class)))
                .thenReturn(ProductStock.builder()
                        .product(Product.builder()
                                .productSeq(1L)
                                .productInfo(ProductInfo.builder()
                                        .price(10000L)
                                        .build())
                                .build())
                        .stock(3L)
                        .build());

        Orders order = orderService.order(request);
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