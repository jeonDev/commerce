package com.commerce.core.order.service;

import com.commerce.core.common.exception.CommerceException;
import com.commerce.core.common.exception.ExceptionStatus;
import com.commerce.core.event.EventTopic;
import com.commerce.core.event.producer.EventSender;
import com.commerce.core.order.domain.OrderDao;
import com.commerce.core.order.domain.entity.OrderDetail;
import com.commerce.core.order.domain.entity.OrderDetailHistory;
import com.commerce.core.order.domain.entity.Orders;
import com.commerce.core.member.domain.entity.Member;
import com.commerce.core.member.service.MemberService;
import com.commerce.core.order.service.request.OrderServiceRequest;
import com.commerce.core.order.service.request.OrderViewMergeServiceRequest;
import com.commerce.core.order.service.request.PaymentServiceRequest;
import com.commerce.core.order.vo.*;
import com.commerce.core.product.domain.entity.Product;
import com.commerce.core.product.domain.entity.ProductInfo;
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

    private final OrderDao orderDao;

    private final ProductStockService productStockService;
    private final MemberService memberService;
    private final EventSender eventSender;
    private final PaymentService paymentService;

    @Transactional
    @Override
    public Orders order(OrderServiceRequest request) {
        log.debug("order : {}",request.toString());
        // 1. Member Use Check
        Member member = memberService.selectUseMember(request.memberSeq())
                .orElseThrow(() -> new CommerceException(ExceptionStatus.ENTITY_IS_EMPTY));

        // 2. Data Setting
        final List<OrderDetail> orderDetails = new ArrayList<>();
        final List<OrderDetailHistory> orderDetailHistories = new ArrayList<>();
        final Orders order = orderDao.save(Orders.builder()
                        .member(member)
                        .build());

        // 3. Product Stock Consume & Order Detail Setting
        Arrays.stream(request.buyProducts())
                .forEach(item -> {
                    OrderDetail orderDetail = this.productStockConsumeAndOrderDetailSetting(order, item);
                    orderDetails.add(orderDetail);
                    orderDetailHistories.add(orderDetail.generateHistoryEntity());
                });

        orderDao.orderDetailSaveAll(orderDetails);
        orderDao.orderDetailHistorySaveAll(orderDetailHistories);

        // 4. 결제 & 결제 시도 안할 시, Event Send
        if (request.payment()) {
            // 4-1. 결제
            PaymentServiceRequest paymentRequest = PaymentServiceRequest.builder()
                    .memberSeq(request.memberSeq())
                    .orderSeq(order.getOrderSeq())
                    .build();
            paymentService.payment(paymentRequest);
        } else {
            // 4-2. Event Send (Order View Mongo DB)
            OrderViewMergeServiceRequest orderViewDto = OrderViewMergeServiceRequest.builder()
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
        return orderDao.findById(orderSeq);
    }

    @Transactional(readOnly = true)
    @Override
    public Optional<OrderDetail> selectOrderDetail(Long orderDetailSeq) {
        return orderDao.orderDetailFindById(orderDetailSeq);
    }

    @Transactional(readOnly = true)
    @Override
    public List<OrderDetail> selectOrderDetailList(Long orderSeq) {
        return orderDao.orderDetailListByOrderSeq(orderSeq);
    }
}
