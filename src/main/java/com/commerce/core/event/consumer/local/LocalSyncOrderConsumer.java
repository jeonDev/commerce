package com.commerce.core.event.consumer.local;

import com.commerce.core.order.service.OrderViewService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@Profile("test")
public class LocalSyncOrderConsumer {
    private final OrderViewService orderViewService;

    public LocalSyncOrderConsumer(OrderViewService orderViewService) {
        this.orderViewService = orderViewService;
    }
}