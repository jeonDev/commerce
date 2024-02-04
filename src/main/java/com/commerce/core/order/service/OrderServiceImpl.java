package com.commerce.core.order.service;

import com.commerce.core.common.exception.CommerceException;
import com.commerce.core.common.exception.ExceptionStatus;
import com.commerce.core.order.entity.OrderDetail;
import com.commerce.core.order.entity.OrderDetailHistory;
import com.commerce.core.order.entity.Orders;
import com.commerce.core.order.repository.OrderDetailHistoryRepository;
import com.commerce.core.order.repository.OrderDetailsRepository;
import com.commerce.core.order.repository.OrdersRepository;
import com.commerce.core.member.entity.Member;
import com.commerce.core.member.service.MemberService;
import com.commerce.core.product.entity.ProductStock;
import com.commerce.core.product.service.ProductStockService;
import com.commerce.core.order.vo.OrderStatus;
import com.commerce.core.order.vo.OrderDto;
import com.commerce.core.product.vo.ProductStockDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Service
public class OrderServiceImpl implements OrderService {

    private final OrdersRepository ordersRepository;
    private final OrderDetailsRepository orderDetailsRepository;
    private final OrderDetailHistoryRepository orderDetailHistoryRepository;

    private final ProductStockService productStockService;
    private final MemberService memberService;


    @Override
    @Transactional
    public Orders order(OrderDto dto) {
        Member member = memberService.selectUseMember(dto.getMemberSeq())
                .orElseThrow(() -> new CommerceException(ExceptionStatus.ENTITY_IS_EMPTY));
        final List<OrderDetail> orderDetails = new ArrayList<>();
        final List<OrderDetailHistory> orderDetailHistories = new ArrayList<>();
        final Orders order = ordersRepository.save(Orders.builder()
                        .member(member)
                        .build());

        Arrays.stream(dto.getProductSeqs())
                .forEach(item -> {
                    ProductStockDto stock = ProductStockDto.builder()
                            .productSeq(item)
                            .stock(-1L)
                            .build();
                    // TODO: 품절 시, 예외 처리 어떻게?
                    ProductStock productStock = productStockService.consume(stock);

                    OrderDetail orderDetail = OrderDetail.builder()
                            .product(productStock.getProduct())
                            .orders(order)
                            .orderStatus(OrderStatus.NEW_ORDER)
                            .build();
                    orderDetails.add(orderDetail);
                    orderDetailHistories.add(orderDetail.generateHistoryEntity());
                });

        orderDetailsRepository.saveAll(orderDetails);
        orderDetailHistoryRepository.saveAll(orderDetailHistories);

        return order;
    }

    @Override
    public Optional<Orders> selectOrder(Long orderSeq) {
        return ordersRepository.findById(orderSeq);
    }

    @Override
    public OrderDetail updateOrderStatus(OrderDto dto) {
        Long orderDetailSeq = dto.getOrderDetailSeq();
        OrderDetail orderDetail = this.selectOrderDetail(orderDetailSeq)
                .orElseThrow(() -> new CommerceException(ExceptionStatus.ENTITY_IS_EMPTY));
        orderDetail.updateOrderStatus(OrderStatus.of(dto.getOrderStatus()));
        orderDetail = orderDetailsRepository.save(orderDetail);

        OrderDetailHistory orderDetailHistory = orderDetail.generateHistoryEntity();
        orderDetailHistoryRepository.save(orderDetailHistory);

        return orderDetail;
    }

    @Override
    public Optional<OrderDetail> selectOrderDetail(Long orderDetailSeq) {
        return orderDetailsRepository.findById(orderDetailSeq);
    }

    @Override
    public List<OrderDetail> selectOrderDetailList(Long orderSeq) {
        return orderDetailsRepository.findByOrders_OrderSeq(orderSeq);
    }
}
