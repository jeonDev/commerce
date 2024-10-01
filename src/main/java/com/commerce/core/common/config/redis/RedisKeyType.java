package com.commerce.core.common.config.redis;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Deprecated
public enum RedisKeyType {

    PRODUCT_STOCK("PS");

    private final String key;
}
