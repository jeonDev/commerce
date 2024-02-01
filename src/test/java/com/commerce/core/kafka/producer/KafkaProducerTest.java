package com.commerce.core.kafka.producer;

import com.commerce.core.event.producer.EventSender;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class KafkaProducerTest {

    @Autowired
    EventSender kafkaEventSender;

    @Test
    void name() {

        kafkaEventSender.send("test");
    }
}
