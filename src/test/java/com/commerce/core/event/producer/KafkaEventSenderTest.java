package com.commerce.core.event.producer;

import com.commerce.core.event.EventTopic;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.kafka.core.KafkaTemplate;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class KafkaEventSenderTest {

    @Mock
    KafkaTemplate kafkaTemplate;

    EventSender eventSender;


    @BeforeEach
    void setUp() {
        eventSender = new KafkaEventSender(kafkaTemplate);
    }

    @Test
    void send() {
        eventSender.send(EventTopic.TOPIC_TEST.getTopic(), "test");
    }
}