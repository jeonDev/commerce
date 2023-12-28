package com.commerce.core.config.redis;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum RedisKeyType {

    PRODUCT_STOCK("PS");

    private final String key;
}
