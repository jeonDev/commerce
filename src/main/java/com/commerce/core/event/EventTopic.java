package com.commerce.core.event;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum EventTopic {
    TOPIC_TEST("topic-test"),
    SYNC_PRODUCT("sync-product"),
    SYNC_ORDER("sync-order"),
    ORDER_COMPLETE("order-complete");

    private final String topic;
}
