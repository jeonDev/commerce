package com.commerce.core.event.consumer.local;

import com.commerce.core.common.exception.CommerceException;
import com.commerce.core.common.exception.ExceptionStatus;
import com.commerce.core.event.LocalEventDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@Profile("test")
public class LocalSyncConsumer {

    private final LocalSyncOrderConsumer localSyncOrderConsumer;
    private final LocalSyncProductConsumer localSyncProductConsumer;

    public LocalSyncConsumer(LocalSyncOrderConsumer localSyncOrderConsumer, LocalSyncProductConsumer localSyncProductConsumer) {
        this.localSyncOrderConsumer = localSyncOrderConsumer;
        this.localSyncProductConsumer = localSyncProductConsumer;
    }

    @EventListener
    public void listener(Object data) {
        if (!(data instanceof LocalEventDto eventData)){
            log.error("Local Sync Consumer 수신 실패 : {}", data.getClass().getName());
            return;
        }

        log.info("Consumer Topic : {} {}", eventData.getTopic(), eventData.getData().toString());

        switch (eventData.getTopic()) {
            case SYNC_PRODUCT -> localSyncProductConsumer.listener(eventData);
            case SYNC_ORDER -> localSyncOrderConsumer.listener(eventData);
            default -> throw new CommerceException(ExceptionStatus.VALID_ERROR);
        }
    }
}