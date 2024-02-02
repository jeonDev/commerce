package com.commerce.core.event.consumer;

import com.commerce.core.common.exception.CommerceException;
import com.commerce.core.common.utils.ConverterUtils;
import com.commerce.core.product.service.ProductViewService;
import com.commerce.core.product.vo.ProductViewDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@RequiredArgsConstructor
@Component
public class KafkaSyncProductConsumer implements EventConsumer {

    private final ProductViewService productViewService;

    /**
     * Kafka Consumer Listener To "sync-product" Topic
     */
    @KafkaListener(topics = "sync-product", groupId = "group_1")
    @Override
    public void listener(Object data) {
        try {
            ConsumerRecord<String, String> record = (ConsumerRecord<String, String>) data;
            log.info(record.toString());

            this.process(ConverterUtils.strToObjectConverter(record.value(), ProductViewDto.class));

            log.info("sync-product Consumer Event Success!");
        } catch (Exception e) {
            log.error("sync-product Consumer Event Fail! : {}", e);
            throw new CommerceException(e);
        }
    }

    private void process(ProductViewDto dto) {
        log.info(dto.toString());
        productViewService.merge(dto);
    }
}
