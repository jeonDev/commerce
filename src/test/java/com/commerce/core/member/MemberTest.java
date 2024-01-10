package com.commerce.core.member;

import com.commerce.core.common.exception.CommerceException;
import com.commerce.core.common.utils.EncryptUtils;
import com.commerce.core.member.entity.Member;
import com.commerce.core.member.service.LoginService;
import com.commerce.core.member.service.MemberService;
import com.commerce.core.member.vo.LoginDto;
import com.commerce.core.member.vo.LoginSuccessDto;
import com.commerce.core.member.vo.MemberDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
public class MemberTest {

    @Autowired
    MemberService memberService;

    @Autowired
    LoginService loginService;

    @Test
    void createMember() {
        MemberDto dto = new MemberDto();
        dto.setId("test");
        dto.setPassword("1234");
        dto.setName("테스트");
        dto.setTel("01011112222");
        dto.setAddr("경기도 주소");
        dto.setAddrDetail("어딘가");
        dto.setZipCode("00000");
        Member member = memberService.createMember(dto);

        Member result = memberService.selectMember(member.getMemberSeq());
        assertThat(result.getId()).isEqualTo(dto.getId());
        assertThat(result.getPassword()).isEqualTo(EncryptUtils.encryptSHA256(dto.getPassword()));
    }

    @Test
    void loginSuccess() {
        LoginDto dto = new LoginDto();
        dto.setId("test");
        dto.setPassword("1234");
        LoginSuccessDto login = loginService.login(dto);
        assertThat(login.getId()).isEqualTo(dto.getId());
    }

    @Test
    void loginFailed() {
        LoginDto dto = new LoginDto();
        dto.setId("test");
        dto.setPassword("12341");

        assertThatThrownBy(() -> loginService.login(dto)).isInstanceOf(CommerceException.class);
    }
}
