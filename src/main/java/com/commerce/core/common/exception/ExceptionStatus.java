package com.commerce.core.common.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ExceptionStatus {

    // MEMBER LOGIN
    LOGIN_FAIL("ML", "로그인에 실패하였습니다."),

    // PRODUCT_STOCK
    SOLD_OUT("PS001", "재고가 품절되었습니다."),

    // COMMON_DATABASE
    ENTITY_IS_EMPTY("CD001", "조회된 데이터가 없습니다.");

    private final String code;
    private final String message;
}
