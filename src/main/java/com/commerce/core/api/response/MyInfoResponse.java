package com.commerce.core.api.response;

import com.commerce.core.member.service.response.MyPageInfoServiceResponse;
import lombok.Builder;

@Builder
public record MyInfoResponse(
        String id,
        String name,
        String tel,
        String addr,
        String addrDetail,
        String zipCode,
        Long point
) {
    public static MyInfoResponse from(MyPageInfoServiceResponse response) {
        return MyInfoResponse.builder()
                .id(response.id())
                .name(response.name())
                .tel(response.tel())
                .addr(response.addr())
                .addrDetail(response.addrDetail())
                .zipCode(response.zipCode())
                .point(response.point())
                .build();
    }
}
