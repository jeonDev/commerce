package com.commerce.core.common.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ExceptionStatus {

    // MEMBER LOGIN
    LOGIN_FAIL("ML01", "로그인에 실패하였습니다."),
    LOGIN_PASSWORD_FAIL("ML02", "패스워드가 틀렸습니다."),
    LOGIN_PASSWORD_FAIL_MAX_COUNT("ML03", "패스워드가 실패 횟수가 초과되었습니다."),

    // PRODUCT_STOCK
    SOLD_OUT("PS001", "재고가 품절되었습니다."),

    // POINT
    POINT_LACK("P001", "잔고 부족"),

    // COMMON_DATABASE
    ENTITY_IS_EMPTY("CD001", "조회된 데이터가 없습니다.");

    private final String code;
    private final String message;
}
