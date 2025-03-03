package com.commerce.core.event.producer.local;

import com.commerce.core.event.type.EventTopic;
import com.commerce.core.event.producer.EventSender;
import com.commerce.core.event.type.local.LocalEventQueue;
import com.commerce.core.event.type.local.QueueData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@Profile("test")
public class LocalEventSender implements EventSender {

    private final LocalEventQueue queue;

    public LocalEventSender(LocalEventQueue queue) {
        this.queue = queue;
    }

    @Override
    public void send(EventTopic topic, Object data) {
        log.info("Event Producer Send (Topic : {} Data : {})", topic, data.toString());
        QueueData queueData = QueueData.builder()
                .topic(topic)
                .data(data)
                .build();
        queue.offer(queueData);
    }
}