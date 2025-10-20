package com.commerce.core.api.request;

import com.commerce.core.member.service.request.MemberUpdateServiceRequest;
import io.swagger.v3.oas.annotations.media.Schema;

public record MemberUpdateRequest(
        @Schema(description = "로그인 아이디", example = "test") String id,
        @Schema(description = "이름", example = "이르음") String name,
        @Schema(description = "패스워드", example = "1234") String password,
        @Schema(description = "전화번호", example = "01011223344") String tel,
        @Schema(description = "주소", example = "서울특별시이") String addr,
        @Schema(description = "상세 주소", example = "어딘가아") String addrDetail,
        @Schema(description = "우편", example = "00000") String zipCode,
        Long point
) {
    public MemberUpdateServiceRequest toRequest() {
        return MemberUpdateServiceRequest.builder()
                .id(id)
                .name(name)
                .tel(tel)
                .addr(addr)
                .addrDetail(addrDetail)
                .zipCode(zipCode)
                .point(point)
                .build();
    }
}
