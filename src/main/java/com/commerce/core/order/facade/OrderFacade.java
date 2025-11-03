package com.commerce.core.order.facade;

import com.commerce.core.order.domain.entity.Orders;
import com.commerce.core.order.service.OrderService;
import com.commerce.core.order.service.request.OrderServiceRequest;
import com.commerce.core.order.type.BuyProduct;
import com.commerce.core.product.domain.entity.ProductStockHistory;
import com.commerce.core.product.service.ProductStockService;
import com.commerce.core.product.type.ProductStockProcessStatus;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;

@Component
public class OrderFacade {
    private final OrderService orderService;
    private final ProductStockService productStockService;

    public OrderFacade(OrderService orderService,
                       ProductStockService productStockService) {
        this.orderService = orderService;
        this.productStockService = productStockService;
    }

    @Transactional
    public Long order(OrderServiceRequest request) {
        // 1. 상품 재고 검증 및 재고 감소
        var productStockHistoryList = Arrays.stream(request.buyProducts())
                .map(this::productStockConsume)
                .toList();

        // 2. 상품 주문 (주문 정보 생성 & 주문 상품 저장)
        Orders order = orderService.order(productStockHistoryList, request.memberSeq(), request.isPayment());

        return order.getOrderSeq();
    }

    private ProductStockHistory productStockConsume(BuyProduct item) {
        return productStockService.productStockAdjustment(item.productSeq(), ProductStockProcessStatus.CONSUME, item.cnt());
    }
}
