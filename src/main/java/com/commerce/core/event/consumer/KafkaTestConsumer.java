package com.commerce.core.event.consumer;

import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class KafkaTestConsumer implements EventConsumer {

    @KafkaListener(topics = "topic-test", groupId = "group_1")
    @Override
    public void listener(Object data) {
        log.info(data.toString());
    }
}
