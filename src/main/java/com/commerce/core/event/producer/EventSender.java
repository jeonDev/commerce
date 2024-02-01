package com.commerce.core.event.producer;

public interface EventSender {
    void send(Object data);
}
