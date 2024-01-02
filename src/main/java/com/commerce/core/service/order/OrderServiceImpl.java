package com.commerce.core.service.order;

import com.commerce.core.common.exception.CommerceException;
import com.commerce.core.common.exception.ExceptionStatus;
import com.commerce.core.entity.*;
import com.commerce.core.entity.repository.OrderDetailHistoryRepository;
import com.commerce.core.entity.repository.OrderDetailsRepository;
import com.commerce.core.entity.repository.OrdersRepository;
import com.commerce.core.service.member.MemberService;
import com.commerce.core.service.product.ProductStockService;
import com.commerce.core.vo.common.type.OrderStatus;
import com.commerce.core.vo.order.OrderDto;
import com.commerce.core.vo.product.ProductStockDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
        Member member = memberService.selectMember(dto.getMemberSeq());
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
    public OrderDetail updateOrderStatus(OrderDto dto) {
        return null;
    }

    @Override
    public OrderDetail selectOrderDetail(Long orderDetailSeq) {
        return orderDetailsRepository.findById(orderDetailSeq).orElseThrow(() -> new CommerceException(ExceptionStatus.ENTITY_IS_EMPTY));
    }
}
