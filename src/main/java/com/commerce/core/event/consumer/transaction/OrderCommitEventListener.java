package com.commerce.core.event.consumer.transaction;

import com.commerce.core.event.EventTopic;
import com.commerce.core.event.producer.EventSender;
import com.commerce.core.event.request.OrderCompleteEventRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Slf4j
@Component
public class OrderCommitEventListener {

    private final EventSender eventSender;

    public OrderCommitEventListener(EventSender eventSender) {
        this.eventSender = eventSender;
    }

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void handleOrderCommitEvent(OrderCompleteEventRequest request) {
        log.info("[ORDER] AFTER_COMMIT");
        eventSender.send(EventTopic.ORDER_COMPLETE, request);
    }

}
