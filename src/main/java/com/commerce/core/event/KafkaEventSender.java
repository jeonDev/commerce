package com.commerce.core.event.producer;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@RequiredArgsConstructor
@Component
public class KafkaEventSender implements EventSender {

    private final KafkaTemplate<String ,Object> kafkaTemplate;

    @Override
    public void send(Object data) {
        log.info("Kafka Producer Send (Data : {})", data.toString());

        kafkaTemplate.send("topic-test", data.toString());
    }
}
