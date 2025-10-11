package com.commerce.core.order.service;

import com.commerce.core.common.exception.CommerceException;
import com.commerce.core.common.exception.ExceptionStatus;
import com.commerce.core.event.EventTopic;
import com.commerce.core.event.producer.EventSender;
import com.commerce.core.member.domain.MemberDao;
import com.commerce.core.member.domain.entity.Member;
import com.commerce.core.order.domain.OrderDao;
import com.commerce.core.order.domain.entity.OrderDetail;
import com.commerce.core.order.domain.entity.OrderDetailHistory;
import com.commerce.core.order.domain.entity.Orders;
import com.commerce.core.order.service.request.OrderServiceRequest;
import com.commerce.core.order.service.request.OrderViewMergeServiceRequest;
import com.commerce.core.order.service.request.PaymentServiceRequest;
import com.commerce.core.order.type.BuyProduct;
import com.commerce.core.order.type.OrderStatus;
import com.commerce.core.product.domain.entity.Product;
import com.commerce.core.product.service.ProductStockService;
import com.commerce.core.product.service.request.ProductStockServiceRequest;
import com.commerce.core.product.type.ProductStockProcessStatus;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;

@Slf4j
@Service
public class OrderService {

    private final OrderDao orderDao;
    private final MemberDao memberDao;
    private final ProductStockService productStockService;
    private final EventSender eventSender;
    private final PaymentService paymentService;

    public OrderService(OrderDao orderDao,
                        MemberDao memberDao,
                        ProductStockService productStockService,
                        EventSender eventSender,
                        PaymentService paymentService) {
        this.orderDao = orderDao;
        this.memberDao = memberDao;
        this.productStockService = productStockService;
        this.eventSender = eventSender;
        this.paymentService = paymentService;
    }

    @Transactional
    public Orders order(OrderServiceRequest request) {
        log.info("[Order] 주문 요청({}) : {} ", request.payment(), request.buyProducts());
        // 1. Member Use Check
        var member = memberDao.findByUsingMemberSeq(request.memberSeq())
                .orElseThrow(() -> new CommerceException(ExceptionStatus.ENTITY_IS_EMPTY));

        // 2. Order
        var order = this.order(member, request.buyProducts());

        // 3. 결제
        if (request.payment()) {
            this.payment(member.getMemberSeq(), order.getOrderSeq());
        } else {
            // 바로 결제 안할 시, Event Send (내역 저장 용)
            var orderViewDto = OrderViewMergeServiceRequest.builder()
                    .orderSeq(order.getOrderSeq())
                    .build();
            eventSender.send(EventTopic.SYNC_ORDER, orderViewDto);
        }

        return order;
    }

    private Orders order(Member member, BuyProduct[] buyProducts) {
        // 1. Order Create
        var order = orderDao.save(Orders.builder()
                .member(member)
                .build());

        // 2. Product Stock Consume & Order Detail Setting
        var orderDetails = Arrays.stream(buyProducts)
                .map(item -> this.productStockConsumeAndOrderDetailSetting(order, item))
                .toList();

        orderDao.orderDetailSaveAll(orderDetails);
        orderDao.orderDetailHistorySaveAll(orderDetails.stream()
                .map(OrderDetailHistory::from)
                .toList()
        );

        return order;
    }

    private OrderDetail productStockConsumeAndOrderDetailSetting(Orders order, BuyProduct item) {
        // 1. Product Stock Consume
        var product = this.productStockConsume(item);
        var productInfo = product.getProductInfo();

        // 2. Order Detail Setting
        return OrderDetail.builder()
                .product(product)
                .cnt(item.cnt())
                .amount(productInfo.getPrice() * item.cnt())
                .buyAmount(productInfo.getPrice() * item.cnt())
                .paidAmount(0L)
                .orders(order)
                .orderStatus(OrderStatus.NEW_ORDER)
                .build();
    }

    private Product productStockConsume(BuyProduct item) {
        var request = ProductStockServiceRequest.builder()
                .productSeq(item.productSeq())
                .stock(item.cnt())
                .productStockProcessStatus(ProductStockProcessStatus.CONSUME)
                .build();

        return productStockService.productStockAdjustment(request)
                .getProduct();
    }

    private void payment(Long memberSeq, Long orderSeq) {
        var paymentRequest = PaymentServiceRequest.builder()
                .memberSeq(memberSeq)
                .orderSeq(orderSeq)
                .build();
        boolean isPayment = paymentService.payment(paymentRequest);

        if (!isPayment) throw new CommerceException(ExceptionStatus.PAYMENT_ERROR);
    }

}
