package com.commerce.core.member.entity;

import com.commerce.core.common.entity.BaseEntity;
import com.commerce.core.common.config.security.vo.Authority;
import com.commerce.core.common.utils.DateUtils;
import com.commerce.core.member.vo.MyPageInfoDto;
import com.commerce.core.member.vo.oauth.OAuthType;
import com.commerce.core.point.entity.MemberPoint;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@DynamicInsert
@DynamicUpdate
@Builder
@Getter
@Table(name = "MEMBER")
public class Member extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "MEMBER_SEQ")
    private Long memberSeq;

    @Column(name = "ID", unique = true)
    private String id;

    @Column(name = "PASSWORD")
    private String password;

    @Column(name = "NAME")
    private String name;

    @Column(name = "TEL")
    private String tel;

    @Column(name = "ADDR")
    private String addr;

    @Column(name = "ADDR_DETAIL")
    private String addrDetail;

    @Column(name = "ZIP_CODE")
    private String zipCode;

    @Column(name = "LAST_LOGIN_DTTM")
    private String lastLoginDttm;

    @Column(name = "PASSWORD_FAIL_COUNT")
    private Long passwordFailCount;

    @Column(name = "USE_YN")
    private String useYn;

    @Column(name = "AUTHORITY")
    @Enumerated(EnumType.STRING)
    private Authority authority;

    @OneToOne(mappedBy = "member", fetch = FetchType.LAZY)
    private MemberPoint memberPoint;

    @Enumerated(EnumType.STRING)
    @Column(name = "OAUTH_TYPE")
    private OAuthType oauthType;

    public void setEncryptPassword(String encPassword) {
        this.password = encPassword;
    }

    public void loginFailed() {
        if(this.passwordFailCount == null) this.passwordFailCount = 0L;
        this.passwordFailCount += 1;
    }

    public void loginSuccess() {
        this.lastLoginDttm = DateUtils.getNowDate("yyyyMMdd");
    }

    public void updateMyPageInfo(MyPageInfoDto dto) {
        this.name = dto.getName();
        this.tel = dto.getTel();
        this.addr = dto.getAddr();
        this.addrDetail = dto.getAddrDetail();
        this.zipCode = dto.getZipCode();
    }
}
