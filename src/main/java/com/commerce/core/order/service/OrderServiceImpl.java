package com.commerce.core.order.service;

import com.commerce.core.common.exception.CommerceException;
import com.commerce.core.common.exception.ExceptionStatus;
import com.commerce.core.event.EventTopic;
import com.commerce.core.event.producer.EventSender;
import com.commerce.core.order.entity.OrderDetail;
import com.commerce.core.order.entity.OrderDetailHistory;
import com.commerce.core.order.entity.Orders;
import com.commerce.core.order.repository.OrderDetailHistoryRepository;
import com.commerce.core.order.repository.OrderDetailsRepository;
import com.commerce.core.order.repository.OrdersRepository;
import com.commerce.core.member.domain.entity.Member;
import com.commerce.core.member.service.MemberService;
import com.commerce.core.order.vo.*;
import com.commerce.core.product.entity.Product;
import com.commerce.core.product.entity.ProductInfo;
import com.commerce.core.product.service.ProductStockService;
import com.commerce.core.product.vo.ProductStockDto;
import com.commerce.core.product.vo.ProductStockProcessStatus;
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
    private final EventSender eventSender;
    private final PaymentService paymentService;

    @Transactional
    @Override
    public Orders order(OrderDto dto) {
        log.debug("order : {}",dto.toString());
        // 1. Member Use Check
        Member member = memberService.selectUseMember(dto.getMemberSeq())
                .orElseThrow(() -> new CommerceException(ExceptionStatus.ENTITY_IS_EMPTY));

        // 2. Data Setting
        final List<OrderDetail> orderDetails = new ArrayList<>();
        final List<OrderDetailHistory> orderDetailHistories = new ArrayList<>();
        final Orders order = ordersRepository.save(Orders.builder()
                        .member(member)
                        .build());

        // 3. Product Stock Consume & Order Detail Setting
        Arrays.stream(dto.getBuyProducts())
                .forEach(item -> {
                    OrderDetail orderDetail = this.productStockConsumeAndOrderDetailSetting(order, item);
                    orderDetails.add(orderDetail);
                    orderDetailHistories.add(orderDetail.generateHistoryEntity());
                });

        orderDetailsRepository.saveAll(orderDetails);
        orderDetailHistoryRepository.saveAll(orderDetailHistories);

        // 4. 결제 & 결제 시도 안할 시, Event Send
        if (dto.isPayment()) {
            // 4-1. 결제
            PaymentDto paymentDto = PaymentDto.builder()
                    .memberSeq(dto.getMemberSeq())
                    .orderSeq(order.getOrderSeq())
                    .build();
            paymentService.payment(paymentDto);
        } else {
            // 4-2. Event Send (Order View Mongo DB)
            OrderViewDto orderViewDto = OrderViewDto.builder()
                    .orderSeq(order.getOrderSeq())
                    .build();
            eventSender.send(EventTopic.SYNC_ORDER, orderViewDto);
        }

        return order;
    }

    private OrderDetail productStockConsumeAndOrderDetailSetting(Orders order, BuyProduct item) {
        // 3-1. Product Stock Consume
        Product product = this.productStockConsume(item);
        ProductInfo productInfo = product.getProductInfo();

        // 3-2. Order Detail Setting
        return OrderDetail.builder()
                .product(product)
                .cnt(item.getCnt())
                .amount(productInfo.getPrice() * item.getCnt())
                .buyAmount(productInfo.getPrice() * item.getCnt())
                .paidAmount(0L)
                .orders(order)
                .orderStatus(OrderStatus.NEW_ORDER)
                .build();
    }

    private Product productStockConsume(BuyProduct item) {
        ProductStockDto stock = ProductStockDto.builder()
                .productSeq(item.getProductSeq())
                .stock(item.getCnt())
                .productStockProcessStatus(ProductStockProcessStatus.CONSUME)
                .build();

        return productStockService.productStockAdjustment(stock).getProduct();
    }

    @Transactional(readOnly = true)
    @Override
    public Optional<Orders> selectOrder(Long orderSeq) {
        return ordersRepository.findById(orderSeq);
    }

    @Transactional(readOnly = true)
    @Override
    public Optional<OrderDetail> selectOrderDetail(Long orderDetailSeq) {
        return orderDetailsRepository.findById(orderDetailSeq);
    }

    @Transactional(readOnly = true)
    @Override
    public List<OrderDetail> selectOrderDetailList(Long orderSeq) {
        return orderDetailsRepository.findByOrders_OrderSeq(orderSeq);
    }
}
