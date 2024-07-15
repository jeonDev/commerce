package com.commerce.core.member.repository.dsl.vo;

import com.commerce.core.common.config.security.vo.Authority;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MemberInfoDAO {
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
    private Long point;

}
