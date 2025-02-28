package com.commerce.core.member.service.response;

import com.commerce.core.api.response.MyInfoResponse;
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

    public MyInfoResponse toResponse() {
        return MyInfoResponse.builder()
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
