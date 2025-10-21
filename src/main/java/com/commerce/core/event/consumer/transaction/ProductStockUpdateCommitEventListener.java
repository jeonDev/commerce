package com.commerce.core.event.consumer.transaction;

import com.commerce.core.event.EventTopic;
import com.commerce.core.event.producer.EventSender;
import com.commerce.core.event.request.ProductEventRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Slf4j
@Component
public class ProductStockUpdateCommitEventListener {

    private final EventSender eventSender;

    public ProductStockUpdateCommitEventListener(EventSender eventSender) {
        this.eventSender = eventSender;
    }

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void handleOrderCommitEvent(ProductEventRequest request) {
        log.info("[PRODUCT}] AFTER_COMMIT");
        eventSender.send(EventTopic.SYNC_PRODUCT, request);
    }
}
