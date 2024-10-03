package com.commerce.core.event.producer;

import com.commerce.core.common.utils.ConverterUtils;
import com.commerce.core.event.EventTopic;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@RequiredArgsConstructor
@Component
@Profile("basic")
public class KafkaEventSender implements EventSender {

    private final KafkaTemplate<String ,Object> kafkaTemplate;

    @Override
    public void send(EventTopic eventTopic, Object data) {
        log.info("Kafka Producer Send (Topic : {} Data : {})", eventTopic, data.toString());
        kafkaTemplate.send(eventTopic.getTopic(), ConverterUtils.strToJsonData(data));
    }
}