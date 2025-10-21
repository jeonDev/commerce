package com.commerce.core.order.service;

import com.commerce.core.common.exception.CommerceException;
import com.commerce.core.common.exception.ExceptionStatus;
import com.commerce.core.event.request.OrderCompleteEventRequest;
import com.commerce.core.member.domain.MemberDao;
import com.commerce.core.order.domain.OrderDao;
import com.commerce.core.order.domain.entity.OrderDetail;
import com.commerce.core.order.domain.entity.Orders;
import com.commerce.core.order.type.OrderStatus;
import com.commerce.core.product.domain.entity.ProductStockHistory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
public class OrderService {

    private final OrderDao orderDao;
    private final MemberDao memberDao;
    private final ApplicationEventPublisher publisher;


    public OrderService(OrderDao orderDao,
                        MemberDao memberDao,
                        ApplicationEventPublisher publisher) {
        this.orderDao = orderDao;
        this.memberDao = memberDao;
        this.publisher = publisher;
    }
    @Transactional
    public Orders save(Long memberSeq) {
        // 1. Member Use Check
        var member = memberDao.findByUsingMemberSeq(memberSeq)
                .orElseThrow(() -> new CommerceException(ExceptionStatus.ENTITY_IS_EMPTY));

        // 2. Order
        return orderDao.save(Orders.builder()
                .member(member)
                .build());
    }

    @Transactional
    public List<OrderDetail> order(Orders order, List<ProductStockHistory> productStockHistoryList, boolean isPayment) {
        List<OrderDetail> orderDetailList = productStockHistoryList.stream()
                .map(item -> this.orderDetailEntitySetting(item, order))
                .toList();

        var eventRequest = new OrderCompleteEventRequest(order.getOrderSeq(),
                order.getMember().getMemberSeq(),
                isPayment
        );
        publisher.publishEvent(eventRequest);

        return orderDetailList;
    }

    private OrderDetail orderDetailEntitySetting(ProductStockHistory productStockHistory, Orders order) {
        var product = productStockHistory.getProduct();
        var productInfo = productStockHistory.getProduct().getProductInfo();
        Long stock = productStockHistory.getStock();

        // 2. Order Detail Setting
        return OrderDetail.builder()
                .product(product)
                .cnt(stock)
                .amount(productInfo.getPrice() * stock)
                .buyAmount(productInfo.getPrice() * stock)
                .paidAmount(0L)
                .orders(order)
                .orderStatus(OrderStatus.NEW_ORDER)
                .build();
    }
}
