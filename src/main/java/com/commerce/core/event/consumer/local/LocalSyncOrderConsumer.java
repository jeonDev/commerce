package com.commerce.core.event.consumer.local;

import com.commerce.core.event.consumer.kafka.AbstractEventConsumer;
import com.commerce.core.order.service.OrderViewService;
import com.commerce.core.order.service.request.OrderViewMergeServiceRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@Profile("test")
public class LocalSyncOrderConsumer extends AbstractEventConsumer<OrderViewMergeServiceRequest> {
    private final OrderViewService orderViewService;

    public LocalSyncOrderConsumer(OrderViewService orderViewService) {
        this.orderViewService = orderViewService;
    }

    @Override
    public void listener(Object data) {
        eventExecuteTemplate(data, OrderViewMergeServiceRequest.class);
    }

    @Override
    public void eventProcess(OrderViewMergeServiceRequest request) {
        orderViewService.merge(request);
    }
}