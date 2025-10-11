package com.commerce.core.member.service;

import com.commerce.core.common.config.security.IdentifierProvider;
import com.commerce.core.common.service.CacheService;
import com.commerce.core.member.domain.entity.Member;
import com.commerce.core.member.service.request.LoginServiceRequest;
import com.commerce.core.member.service.response.LoginServiceResponse;
import io.jsonwebtoken.impl.DefaultClaims;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
@DisplayName("로그인 서비스")
class LoginServiceTest {

    @Mock
    MemberService memberService;
    @Mock
    IdentifierProvider identifierProvider;

    @Mock
    PasswordEncoder passwordEncoder;

    @Mock
    CacheService<String, String> cacheService;

    LoginService loginService;


    @BeforeEach
    void setUp() {
        loginService = new LoginService(memberService,
                identifierProvider, passwordEncoder, cacheService);
    }

    @Test
    @DisplayName("로그인 성공")
    void 로그인_성공() {
        // given
        LoginServiceRequest dto = LoginServiceRequest.builder()
                .id("test")
                .password("1234")
                .build();

        Optional<Member> member = Optional.of(Member.builder()
                        .id("test")
                        .password("1234")
                        .passwordFailCount(0L)
                        .build()
        );
        Mockito.when(memberService.selectUseMember("test"))
                        .thenReturn(member);
        Mockito.when(passwordEncoder.matches(Mockito.any(), Mockito.any()))
                        .thenReturn(true);
        Mockito.when(identifierProvider.generateIdentificationInfo(Mockito.any()))
                        .thenReturn("token");

        // when
        LoginServiceResponse login = loginService.login(dto);

        // then
        assertThat(login.accessToken()).isEqualTo("token");
        assertThat(login.id()).isEqualTo("test");
    }

    @Test
    @DisplayName("토큰 재발급 성공")
    void 토큰재발급_성공() {
        // given
        String accessToken = "Bearer token";
        String refreshToken = "refresh-token";

        Mockito.when(cacheService.getCache(Mockito.any()))
                .thenReturn(refreshToken);

        Mockito.when(identifierProvider.getTokenForSubject(Mockito.any()))
                .thenReturn(new DefaultClaims());

        Mockito.when(identifierProvider.generateIdentificationInfo(Mockito.any()))
                .thenReturn("new-token");

        // when
        String result = loginService.tokenReIssue(accessToken, refreshToken);

        // then
        assertThat(result).isEqualTo("new-token");
    }
}