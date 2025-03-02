package com.commerce.core.event.consumer.kafka;

import com.commerce.core.order.service.OrderViewService;
import com.commerce.core.order.service.request.OrderViewMergeServiceRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@RequiredArgsConstructor
@Component
@Profile("basic")
public class KafkaSyncOrderConsumer extends AbstractEventConsumer<OrderViewMergeServiceRequest> {
    private final static String TOPIC_NAME = "sync-order";
    private final static String GROUP_ID = "group_1";

    private final OrderViewService orderViewService;

    /**
     * sync-order Topic Listener
     * kafka-topics.sh --create --topic sync-order --bootstrap-server localhost:9092 --replication-factor 1 --partitions 3
     */
    @KafkaListener(topics = TOPIC_NAME, groupId = GROUP_ID)
    @Override
    public void listener(Object data) {
        eventExecuteTemplate(data, OrderViewMergeServiceRequest.class);
    }

    @Override
    public void eventProcess(OrderViewMergeServiceRequest request) {
        log.info(request.toString());
        orderViewService.merge(request);
    }
}