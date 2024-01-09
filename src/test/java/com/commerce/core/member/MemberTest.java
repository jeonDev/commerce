package com.commerce.core.member;

import com.commerce.core.member.entity.Member;
import com.commerce.core.member.service.MemberService;
import com.commerce.core.member.vo.MemberDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class MemberTest {

    @Autowired
    MemberService memberService;

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
    }

    @Test
    void loginFailed() {

    }
}
