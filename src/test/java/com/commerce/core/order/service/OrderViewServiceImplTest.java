package com.commerce.core.order.service;

import com.commerce.core.order.entity.OrderDetail;
import com.commerce.core.order.entity.Orders;
import com.commerce.core.order.repository.mongo.OrderViewRepository;
import com.commerce.core.order.vo.OrderStatus;
import com.commerce.core.order.vo.OrderViewDto;
import com.commerce.core.product.entity.Product;
import com.commerce.core.product.entity.ProductInfo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("주문이벤트_서비스")
class OrderViewServiceImplTest {

    @Mock
    private OrderViewRepository orderViewRepository;
    @Mock
    private OrderService orderService;

    private OrderViewService orderViewService;

    @BeforeEach
    void setUp() {
        orderViewService = new OrderViewServiceImpl(orderViewRepository, orderService);
    }

    @Test
    @DisplayName("주문 이벤트")
    void 주문이벤트_성공() {
        OrderViewDto orderViewDto = OrderViewDto.builder()
                .orderSeq(1L)
                .build();

        ProductInfo productInfo = ProductInfo.builder()
                .productInfoSeq(1L)
                .productName("123")
                .build();
        OrderDetail orderDetail1 = OrderDetail.builder()
                .orderDetailSeq(1L)
                .orders(Orders.builder().orderSeq(1L).build())
                .product(Product.builder().productSeq(1L).productInfo(productInfo).build())
                .cnt(2L)
                .amount(10000L)
                .buyAmount(10000L)
                .paidAmount(0L)
                .orderStatus(OrderStatus.NEW_ORDER)
                .build();
        OrderDetail orderDetail2 = OrderDetail.builder()
                .orderDetailSeq(1L)
                .orders(Orders.builder().orderSeq(1L).build())
                .product(Product.builder().productSeq(2L).productInfo(productInfo).build())
                .cnt(2L)
                .amount(15000L)
                .buyAmount(15000L)
                .paidAmount(0L)
                .orderStatus(OrderStatus.NEW_ORDER)
                .build();

        Mockito.when(orderService.selectOrderDetailList(Mockito.anyLong()))
                        .thenReturn(List.of(orderDetail1, orderDetail2));
        Mockito.when(orderViewRepository.findByOrderSeq(Mockito.anyLong()))
                        .thenReturn(Optional.ofNullable(null));

        orderViewService.merge(orderViewDto);
    }
}