package com.commerce.core.event.consumer.kafka;

import com.commerce.core.common.exception.CommerceException;
import com.commerce.core.common.exception.ExceptionStatus;
import com.commerce.core.common.utils.ConverterUtils;
import com.commerce.core.event.LocalEventDto;
import com.commerce.core.event.consumer.EventConsumer;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;

/**
 * Event Consumer 추상클래스
 * 1. Object Data -> Generic Class Converter
 * 2. Process
 */
@Slf4j
public abstract class AbstractEventConsumer<T> implements EventConsumer {

    /** Event Consumer 템플릿 */
    public void eventExecuteTemplate(Object data, Class<T> tClass) {
        try {
            T convertData = this.dataConverter(data, tClass);

            eventProcess(convertData);

            log.info("Consumer Event Success!");
        } catch (Exception e) {
            log.error("Consumer Event Fail! : {}", e.getMessage());
            throw new CommerceException(e);
        }
    }

    /** Consumer 데이터 처리 프로세스 */
    public abstract void eventProcess(T data);

    private T dataConverter(Object data, Class<T> tClass) {
        if (data instanceof ConsumerRecord){
            return this.kafkaDataConverter(data, tClass);
        } else if (data instanceof LocalEventDto eventData) {
            return this.testDataConverter(eventData);
        } else {
            throw new CommerceException(ExceptionStatus.VALID_ERROR);
        }
    }

    // kafka convert
    private T kafkaDataConverter(Object data, Class<T> tClass) {
        ConsumerRecord<String, String> record = (ConsumerRecord<String, String>) data;
        log.info("Consumer Record Value : {}" ,record.toString());

        return ConverterUtils.strToObjectConverter(record.value(), tClass);
    }

    // test convert
    private T testDataConverter(LocalEventDto eventData) {
        return (T) eventData.getData();
    }
}