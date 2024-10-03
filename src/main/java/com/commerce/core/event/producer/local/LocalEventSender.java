package com.commerce.core.event.producer.local;

import com.commerce.core.event.EventTopic;
import com.commerce.core.event.LocalEventDto;
import com.commerce.core.event.producer.EventSender;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@Profile("test")
public class LocalEventSender implements EventSender {

    private final ApplicationEventPublisher publisher;

    public LocalEventSender(ApplicationEventPublisher publisher) {
        this.publisher = publisher;
    }

    @Override
    public void send(EventTopic topic, Object data) {
        log.info("Event Producer Send (Topic : {} Data : {})", topic, data.toString());
        LocalEventDto eventData = LocalEventDto.builder()
                .topic(topic)
                .data(data)
                .build();
        publisher.publishEvent(eventData);
    }
}