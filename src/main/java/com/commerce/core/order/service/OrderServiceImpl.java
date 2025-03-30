package com.commerce.core.order.service;

import com.commerce.core.common.exception.CommerceException;
import com.commerce.core.common.exception.ExceptionStatus;
import com.commerce.core.event.EventTopic;
import com.commerce.core.event.producer.EventSender;
import com.commerce.core.member.domain.entity.Member;
import com.commerce.core.member.service.MemberService;
import com.commerce.core.order.domain.OrderDao;
import com.commerce.core.order.domain.entity.OrderDetail;
import com.commerce.core.order.domain.entity.OrderDetailHistory;
import com.commerce.core.order.domain.entity.Orders;
import com.commerce.core.order.service.request.OrderServiceRequest;
import com.commerce.core.order.service.request.OrderViewMergeServiceRequest;
import com.commerce.core.order.service.request.PaymentServiceRequest;
import com.commerce.core.order.vo.BuyProduct;
import com.commerce.core.order.vo.OrderStatus;
import com.commerce.core.product.domain.entity.Product;
import com.commerce.core.product.domain.entity.ProductInfo;
import com.commerce.core.product.service.ProductStockService;
import com.commerce.core.product.service.request.ProductStockServiceRequest;
import com.commerce.core.product.vo.ProductStockProcessStatus;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
        log.info("[Order] 주문 요청({}) : {} ", request.payment(), request.buyProducts());
        // 1. Member Use Check
        Member member = memberService.selectUseMember(request.memberSeq())
                .orElseThrow(() -> new CommerceException(ExceptionStatus.ENTITY_IS_EMPTY));

        // 2. Order
        Orders order = this.order(member, request.buyProducts());

        // 3. 결제
        if (request.payment()) {
            this.payment(member.getMemberSeq(), order.getOrderSeq());
        } else {
            // 바로 결제 안할 시, Event Send (내역 저장 용)
            OrderViewMergeServiceRequest orderViewDto = OrderViewMergeServiceRequest.builder()
                    .orderSeq(order.getOrderSeq())
                    .build();
            eventSender.send(EventTopic.SYNC_ORDER, orderViewDto);
        }

        return order;
    }

    private Orders order(Member member, BuyProduct[] buyProducts) {
        // 1. Order Create
        final Orders order = orderDao.save(Orders.builder()
                .member(member)
                .build());

        // 2. Product Stock Consume & Order Detail Setting
        List<OrderDetail> orderDetails = Arrays.stream(buyProducts)
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
        Product product = this.productStockConsume(item);
        ProductInfo productInfo = product.getProductInfo();

        // 2. Order Detail Setting
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
        ProductStockServiceRequest request = ProductStockServiceRequest.builder()
                .productSeq(item.getProductSeq())
                .stock(item.getCnt())
                .productStockProcessStatus(ProductStockProcessStatus.CONSUME)
                .build();

        return productStockService.productStockAdjustment(request)
                .getProduct();
    }

    private void payment(Long memberSeq, Long orderSeq) {
        PaymentServiceRequest paymentRequest = PaymentServiceRequest.builder()
                .memberSeq(memberSeq)
                .orderSeq(orderSeq)
                .build();
        paymentService.payment(paymentRequest);
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
