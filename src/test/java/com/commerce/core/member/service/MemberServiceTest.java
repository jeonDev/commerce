package com.commerce.core.member.service;

import com.commerce.core.member.entity.Member;
import com.commerce.core.member.repository.MemberRepository;
import com.commerce.core.member.repository.dsl.MemberDslRepository;
import com.commerce.core.member.vo.MemberDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
@DisplayName("고객 서비스")
class MemberServiceTest {

    @Mock
    MemberRepository memberRepository;
    @Mock
    PasswordEncoder passwordEncoder;
    @Mock
    MemberDslRepository memberDslRepository;

    MemberService memberService;


    @BeforeEach
    void setUp() {
        memberService = new MemberServiceImpl(memberRepository, memberDslRepository, passwordEncoder);
    }

    @Test
    @DisplayName("고객정보생성")
    void 고객정보생성_성공() {
        // given
        MemberDto memberDto = new MemberDto();
        memberDto.setId("new-id");
        memberDto.setPassword("1234");
        memberDto.setAddr("주소");

        Mockito.when(passwordEncoder.encode(Mockito.any()))
                .thenReturn("9999");

        Mockito.when(memberRepository.save(Mockito.any()))
                .thenReturn(memberDto.dtoToEntity());

        // when
        Member member = memberService.createMember(memberDto);

        // then
        assertThat(member.getId()).isEqualTo(memberDto.getId());
//        assertThat(member.getPassword()).isEqualTo("9999");
        assertThat(member.getAddr()).isEqualTo(memberDto.getAddr());
    }
}