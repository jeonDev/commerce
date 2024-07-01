package com.commerce.core.member.vo;

import com.commerce.core.common.config.security.vo.Authority;
import com.commerce.core.member.entity.Member;
import lombok.Data;

@Data
public class MemberDto {

    private Long memberSeq;
    private String id;
    private String name;
    private String password;
    private String tel;
    private String addr;
    private String addrDetail;
    private String zipCode;

    public Member dtoToEntity() {
        return Member.builder()
                .id(id)
                .memberSeq(memberSeq)
                .name(name)
                .password(password)
                .tel(tel)
                .addr(addr)
                .addrDetail(addrDetail)
                .zipCode(zipCode)
                .passwordFailCount(0L)
                .authority(Authority.ROLE_USER)
                .useYn("Y")
                .build();
    }
}
