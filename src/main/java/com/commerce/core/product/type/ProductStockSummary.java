package com.commerce.core.product.type;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

@Getter
@AllArgsConstructor
public enum ProductStockSummary {

    NOT_IN_STOCK("재고 없음"),
    SMALL_STOCK("5개 미만 존재"),
    MANY_STOCK("5개 이상 존재");

    private final String status;

    @JsonCreator
    public static ProductStockSummary from(String value) {
        return Arrays.stream(values())
                .filter(v -> v.status.equals(value))
                .findAny()
                .orElseThrow();
    }

    public static ProductStockSummary of(Long stock) {
        if (stock.equals(0L))
            return ProductStockSummary.NOT_IN_STOCK;
        if (stock.compareTo(0L) > 0 && stock.compareTo(5L) < 0)
            return ProductStockSummary.SMALL_STOCK;

        return ProductStockSummary.MANY_STOCK;
    }
}
