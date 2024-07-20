package com.commerce.core.common.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ExceptionStatus {

    // Auth
    AUTH_UNAUTHORIZED("A401", "로그인 정보가 없습니다."),
    AUTH_FORBIDDEN("A403", "권한이 없습니다."),
    AUTH_TOKEN_UN_MATCH("A409", "토큰 정보가 일치하지 않습니다."),
    AUTH_REFRESH_TOKEN_FAIL("A410", "토큰 재발급 실패"),

    // MEMBER LOGIN
    LOGIN_FAIL("ML01", "로그인에 실패하였습니다."),
    LOGIN_PASSWORD_FAIL("ML02", "패스워드가 틀렸습니다."),
    LOGIN_PASSWORD_FAIL_MAX_COUNT("ML03", "패스워드가 실패 횟수가 초과되었습니다."),
    LOGIN_NOT_EXISTS_ID("ML04", "아이디가 존재하지 않습니다."),

    // PRODUCT_STOCK
    SOLD_OUT("PS001", "재고가 품절되었습니다."),

    // POINT
    POINT_LACK("P001", "잔고 부족"),

    // PAYMENT
    PAYMENT_AMOUNT_ERROR("PA001", "결제금액 오류"),

    // COMMON_DATABASE
    ENTITY_IS_EMPTY("CD001", "조회된 데이터가 없습니다."),

    // COMMON lock occupied
    LOCK_OCCUPIED_ERROR("C001", "Lock 점유 실패"),
    VALID_ERROR("C998", "Valid Error"),
    SYSTEM_ERROR("C999", "System Error");

    private final String code;
    private final String message;
}
