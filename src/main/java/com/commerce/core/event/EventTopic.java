package com.commerce.core.event;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum EventTopic {
    SYNC_PRODUCT("sync-product"),
    ORDER_COMPLETE("order-complete");

    private final String topic;
}
