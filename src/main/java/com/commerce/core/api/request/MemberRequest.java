package com.commerce.core.api.request;

import com.commerce.core.member.service.request.MemberServiceRequest;
import com.commerce.core.member.type.oauth.OAuthType;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;

public record MemberRequest(
        @Schema(description = "로그인 아이디", example = "test") @NotNull String id,
        @Schema(description = "이름", example = "이르음") @NotNull String name,
        @Schema(description = "패스워드", example = "1234") @NotNull String password,
        @Schema(description = "전화번호", example = "01011223344") @NotNull String tel,
        @Schema(description = "주소", example = "서울특별시이") @NotNull String addr,
        @Schema(description = "상세 주소", example = "어딘가아") @NotNull String addrDetail,
        @Schema(description = "우편", example = "00000") @NotNull String zipCode,
        @Schema(description = "소셜로그인 타입", example = "") @Null OAuthType oAuthType
) {

    public MemberServiceRequest toRequest() {
        return MemberServiceRequest.builder()
                .id(id)
                .name(name)
                .password(password)
                .tel(tel)
                .addr(addr)
                .addrDetail(addrDetail)
                .zipCode(zipCode)
                .oAuthType(oAuthType)
                .build();
    }
}
