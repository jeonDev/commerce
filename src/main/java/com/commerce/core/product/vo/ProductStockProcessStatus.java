package com.commerce.core.product.vo;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

@Getter
@AllArgsConstructor
public enum ProductStockProcessStatus {
    
    ADD("0"),           // 추가
    CONSUME("1");       // 차감
    
    private final String status;
    
    @JsonCreator
    public static ProductStockProcessStatus from(String value) {
        return Arrays.stream(values())
                .filter(v -> v.status.equals(value))
                .findAny()
                .orElseThrow();
    }
}
