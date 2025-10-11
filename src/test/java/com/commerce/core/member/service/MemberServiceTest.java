package com.commerce.core.member.service;

import com.commerce.core.member.domain.MemberDao;
import com.commerce.core.member.domain.entity.Member;
import com.commerce.core.member.service.request.MemberServiceRequest;
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
    MemberDao memberDao;
    @Mock
    PasswordEncoder passwordEncoder;

    MemberService memberService;


    @BeforeEach
    void setUp() {
        memberService = new MemberService(memberDao, passwordEncoder);
    }

    @Test
    @DisplayName("고객정보생성")
    void 고객정보생성_성공() {
        // given
        MemberServiceRequest memberDto = MemberServiceRequest.builder()
                .id("new-id")
                .password("1234")
                .addr("주소")
                .build();

        Mockito.when(passwordEncoder.encode(Mockito.any()))
                .thenReturn("9999");

        Mockito.when(memberDao.save(Mockito.any()))
                .thenReturn(memberDto.toEntity());

        // when
        Member member = memberService.createMember(memberDto);

        // then
        assertThat(member.getId()).isEqualTo(memberDto.id());
//        assertThat(member.getPassword()).isEqualTo("9999");
        assertThat(member.getAddr()).isEqualTo(memberDto.addr());
    }
}