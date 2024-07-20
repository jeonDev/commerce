package com.commerce.core.member.vo;

import com.commerce.core.common.config.security.vo.Authority;
import com.commerce.core.member.entity.Member;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class MemberDto {

    private Long memberSeq;
    @NotNull
    private String id;
    @NotNull
    private String name;
    @NotNull
    private String password;
    @NotNull
    private String tel;
    @NotNull
    private String addr;
    @NotNull
    private String addrDetail;
    @NotNull
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
