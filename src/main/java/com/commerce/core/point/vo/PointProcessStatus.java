package com.commerce.core.point.vo;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

@Getter
@AllArgsConstructor
public enum PointProcessStatus {

    CHARGE("0"),        // 충전
    PAYMENT("1");       // 결제

    private final String status;

    @JsonCreator
    public static PointProcessStatus from(String value) {
        return Arrays.stream(values())
                .filter(v -> v.status.equals(value))
                .findAny()
                .orElseThrow();
    }
}
