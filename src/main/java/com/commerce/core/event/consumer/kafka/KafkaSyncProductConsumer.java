package com.commerce.core.event.consumer.kafka;

import com.commerce.core.product.service.ProductViewService;
import com.commerce.core.event.request.ProductViewEventRequest;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@RequiredArgsConstructor
@Component
public class KafkaSyncProductConsumer extends AbstractEventConsumer<ProductViewEventRequest>  {

    private final static String TOPIC_NAME = "sync-product";
    private final static String GROUP_ID = "group_1";
    private final ProductViewService productViewService;

    /**
     * sync-product Topic Listener
     * kafka-topics.sh --create --topic sync-product --bootstrap-server localhost:9092 --replication-factor 1 --partitions 3
     */
    @KafkaListener(topics = TOPIC_NAME, groupId = GROUP_ID)
    @Override
    public void listener(Object data) {
        log.info("[Kakfa] Event Receive : {}", TOPIC_NAME);
        eventExecuteTemplate(data, ProductViewEventRequest.class);
    }

    @Override
    public void eventProcess(ProductViewEventRequest request) {
        log.info(request.toString());
        productViewService.merge(request);
    }
}
