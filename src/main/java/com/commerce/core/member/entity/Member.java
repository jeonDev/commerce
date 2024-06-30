package com.commerce.core.member.entity;

import com.commerce.core.common.entity.BaseEntity;
import com.commerce.core.common.security.vo.Authority;
import com.commerce.core.common.utils.DateUtils;
import com.commerce.core.common.utils.EncryptUtils;
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
}
