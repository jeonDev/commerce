package com.commerce.core.member.domain.dto;

import com.commerce.core.common.config.security.vo.Authority;
import com.commerce.core.member.vo.oauth.OAuthType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MemberInfoDto {
    private Long memberSeq;
    private String id;
    private String name;
    private String tel;
    private String addr;
    private String addrDetail;
    private String zipCode;
    private String lastLoginDttm;
    private String passwordFailCount;
    private String useYn;
    private Authority authority;
    private OAuthType oAuthType;
    private Long point;

}
