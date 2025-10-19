package com.commerce.core.order.facade;

import com.commerce.core.event.request.OrderCompleteEventRequest;
import com.commerce.core.order.service.OrderService;
import com.commerce.core.order.service.request.OrderServiceRequest;
import com.commerce.core.order.type.BuyProduct;
import com.commerce.core.product.domain.entity.ProductStockHistory;
import com.commerce.core.product.service.ProductStockService;
import com.commerce.core.product.service.request.ProductStockServiceRequest;
import com.commerce.core.product.type.ProductStockProcessStatus;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;

@Component
public class OrderFacade {
    private final OrderService orderService;
    private final ProductStockService productStockService;
    private final ApplicationEventPublisher publisher;

    public OrderFacade(OrderService orderService,
                       ProductStockService productStockService,
                       ApplicationEventPublisher publisher) {
        this.orderService = orderService;
        this.productStockService = productStockService;
        this.publisher = publisher;
    }

    @Transactional
    public Long order(OrderServiceRequest request) {
        // 1. 주문 정보 생성
        var order = orderService.save(request.memberSeq());

        // 2. 상품 재고 검증 및 재고 감소
        var productStockHistoryList = Arrays.stream(request.buyProducts())
                .map(this::productStockConsume)
                .toList();

        // 3. 주문 상품 저장
        orderService.order(order, productStockHistoryList);

        var eventRequest = new OrderCompleteEventRequest(order.getOrderSeq(),
                request.memberSeq(),
                request.isPayment()
        );
        publisher.publishEvent(eventRequest);
        return order.getOrderSeq();
    }

    private ProductStockHistory productStockConsume(BuyProduct item) {
        var request = ProductStockServiceRequest.builder()
                .productSeq(item.productSeq())
                .stock(item.cnt())
                .productStockProcessStatus(ProductStockProcessStatus.CONSUME)
                .build();

        return productStockService.productStockAdjustment(request);
    }
}
