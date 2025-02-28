package com.commerce.core.api.request;

import com.commerce.core.member.service.request.MemberUpdateServiceRequest;

public record MemberUpdateRequest(
        String id,
        String name,
        String tel,
        String addr,
        String addrDetail,
        String zipCode,
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
