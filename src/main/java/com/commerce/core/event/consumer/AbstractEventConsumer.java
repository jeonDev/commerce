package com.commerce.core.event.consumer;

import com.commerce.core.common.exception.CommerceException;
import com.commerce.core.common.utils.ConverterUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;

/**
 * Event Consumer 추상클래스
 * 1. Object Data -> Generic Class Converter
 * 2. Process
 */
@Slf4j
public abstract class AbstractEventConsumer<T> implements EventConsumer {

    /**
     * Event Consumer 템플릿
     */
    public void eventExecuteTemplate(Object data, Class<T> tClass) {
        try {
            ConsumerRecord<String, String> record = (ConsumerRecord<String, String>) data;
            log.info("Consumer Record Value : {}" ,record.toString());

            T convertData = ConverterUtils.strToObjectConverter(record.value(), tClass);
            eventProcess(convertData);

            log.info("Consumer Event Success!");
        } catch (Exception e) {
            log.error("Consumer Event Fail! : {}", e);
            throw new CommerceException(e);
        }
    }

    /**
     * Consumer 데이터 처리 프로세스
     */
    public abstract void eventProcess(T data);

}