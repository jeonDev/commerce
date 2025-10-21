package com.commerce.core.order.service;

import com.commerce.core.common.exception.CommerceException;
import com.commerce.core.common.exception.ExceptionStatus;
import com.commerce.core.event.request.OrderCompleteEventRequest;
import com.commerce.core.member.domain.MemberDao;
import com.commerce.core.order.domain.OrderDao;
import com.commerce.core.order.domain.entity.OrderDetail;
import com.commerce.core.order.domain.entity.OrderDetailHistory;
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
    public Orders order(List<ProductStockHistory> productStockHistoryList, Long memberSeq, boolean isPayment) {
        // 1. 고객 정보 체크
        var member = memberDao.findByUsingMemberSeq(memberSeq)
                .orElseThrow(() -> new CommerceException(ExceptionStatus.ENTITY_IS_EMPTY));

        // 2. 주문 정보 생성
        Orders order = orderDao.save(Orders.builder()
                .member(member)
                .build());

        // 3. 주문 상품 정보 생성 & 내역 저장
        List<OrderDetail> orderDetailList = productStockHistoryList.stream()
                .map(item -> this.orderDetailEntitySetting(item, order))
                .toList();

        orderDao.orderDetailSaveAll(orderDetailList);

        List<OrderDetailHistory> orderDetailHistoryList = orderDetailList.stream()
                .map(OrderDetailHistory::from)
                .toList();

        orderDao.orderDetailHistorySaveAll(orderDetailHistoryList);

        // 4. 주문 완료 이벤트 호출 (트랜잭션 커밋)
        publisher.publishEvent(order.orderCompleteMakeEventPublisherRequest(isPayment));

        return order;
    }

    private OrderDetail orderDetailEntitySetting(ProductStockHistory productStockHistory, Orders order) {
        var product = productStockHistory.getProduct();
        var productInfo = productStockHistory.getProduct().getProductInfo();
        Long stock = Math.abs(productStockHistory.getStock());

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
