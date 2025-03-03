package com.commerce.core.event.type.local;

public interface EventQueue {

    void offer(Object o);
    Object poll();
}
