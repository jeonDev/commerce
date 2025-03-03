package com.commerce.core.event.producer;

import com.commerce.core.event.type.EventTopic;

public interface EventSender {
    void send(EventTopic topic, Object data);
}
