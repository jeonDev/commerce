package com.commerce.core.member.domain.entity;

import com.commerce.core.common.entity.BaseEntity;
import com.commerce.core.common.config.security.type.Authority;
import com.commerce.core.common.utils.DateUtils;
import com.commerce.core.member.service.request.MemberUpdateServiceRequest;
import com.commerce.core.member.type.oauth.OAuthType;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

@Entity
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@DynamicInsert
@DynamicUpdate
@Getter
@Table(name = "MEMBER")
public class Member extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "MEMBER_SEQ")
    private Long memberSeq;

    @Column(name = "LOGIN_ID", unique = true)
    private String loginId;

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

    @Enumerated(EnumType.STRING)
    @Column(name = "OAUTH_TYPE")
    private OAuthType oauthType;

    public static Member of(
            String loginId,
            String password,
            String name,
            String tel,
            String addr,
            String addrDetail,
            String zipCode,
            Authority authority,
            OAuthType oAuthType
    ) {
        return new Member(
                null,
                loginId,
                password,
                name,
                tel,
                addr,
                addrDetail,
                zipCode,
                null,
                0L,
                "Y",
                authority,
                oAuthType
        );
    }

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

    public Member updateMyPageInfo(MemberUpdateServiceRequest request) {
        this.name = request.name();
        this.tel = request.tel();
        this.addr = request.addr();
        this.addrDetail = request.addrDetail();
        this.zipCode = request.zipCode();
        return this;
    }
}
