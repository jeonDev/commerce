package com.commerce.core.event.consumer.local;

import com.commerce.core.event.type.local.QueueData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Slf4j
@Configuration
@Profile("test")
public class LocalEventListener {


    public void consume(QueueData queueData) {
        log.info("data : {} , topic : {}", queueData.data(), queueData.topic());
    }


}
