package com.commerce.core.member.service.response;

import com.commerce.core.member.domain.dto.MemberInfoDto;
import lombok.Builder;

@Builder
public record MyPageInfoServiceResponse (
        String id,
        String name,
        String tel,
        String addr,
        String addrDetail,
        String zipCode,
        Long point
) {

    public static MyPageInfoServiceResponse from(MemberInfoDto response) {
        return MyPageInfoServiceResponse.builder()
                .id(response.getId())
                .name(response.getName())
                .tel(response.getTel())
                .addr(response.getAddr())
                .addrDetail(response.getAddrDetail())
                .zipCode(response.getZipCode())
                .point(response.getPoint())
                .build();
    }

}
