package com.commerce.core.event.consumer.kafka;

import com.commerce.core.event.request.OrderCompleteEventRequest;
import com.commerce.core.order.facade.PaymentFacade;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@RequiredArgsConstructor
@Component
public class KafkaOrderCompleteConsumer extends AbstractEventConsumer<OrderCompleteEventRequest>{
    private final static String TOPIC_NAME = "order-complete";
    private final static String GROUP_ID = "group_1";
    private final PaymentFacade paymentFacade;


    // kafka-topics.sh --create --topic order-complete --bootstrap-server localhost:9092 --partitions 3 --replication-factor 1
    @KafkaListener(topics = TOPIC_NAME, groupId = GROUP_ID)
    @Override
    public void listener(Object data) {
        eventExecuteTemplate(data, OrderCompleteEventRequest.class);

    }

    @Override
    public void eventProcess(OrderCompleteEventRequest data) {
        if (data.isPayment()) {
            boolean payment = paymentFacade.payment(data.orderSeq(), data.memberSeq());
        }
    }
}
