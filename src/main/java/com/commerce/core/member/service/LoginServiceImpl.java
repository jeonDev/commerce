package com.commerce.core.member.service;

import com.commerce.core.common.service.CacheService;
import com.commerce.core.common.exception.CommerceException;
import com.commerce.core.common.exception.ExceptionStatus;
import com.commerce.core.common.config.security.IdentifierProvider;
import com.commerce.core.common.config.security.vo.IdentificationGenerateVO;
import com.commerce.core.common.config.security.vo.JwtIdentificationGenerateVO;
import com.commerce.core.common.config.security.vo.JwtToken;
import com.commerce.core.member.entity.Member;
import com.commerce.core.member.vo.LoginDto;
import com.commerce.core.member.vo.LoginSuccessDto;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@RequiredArgsConstructor
@Service
public class LoginServiceImpl implements LoginService {

    private final Long MAX_PASSWORD_WRONG_COUNT = 5L;
    private final MemberService memberService;
    private final IdentifierProvider jwtTokenProvider;
    private final PasswordEncoder passwordEncoder;
    private final CacheService<String, String> redisService;

    @Transactional(noRollbackFor = CommerceException.class)
    @Override
    public LoginSuccessDto login(LoginDto dto) {
        String id = dto.getId();

        Member member = memberService.selectUseMember(id)
                .orElseThrow(() -> new CommerceException(ExceptionStatus.LOGIN_NOT_EXISTS_ID));

        // passwordFailCount >= 5
        if(member.getPasswordFailCount() >= MAX_PASSWORD_WRONG_COUNT) {
            throw new CommerceException(ExceptionStatus.LOGIN_NOT_EXISTS_ID);
        }

        // Login Success
        if(passwordEncoder.matches(dto.getPassword(), member.getPassword())) {
            log.info("Login Success");
            IdentificationGenerateVO accessTokenVO = JwtIdentificationGenerateVO.builder()
                    .jwtToken(JwtToken.ACCESS_TOKEN)
                    .id(member.getId())
                    .build();
            String accessToken = (String) jwtTokenProvider.generateIdentificationInfo(accessTokenVO);

            IdentificationGenerateVO refreshTokenVO = JwtIdentificationGenerateVO.builder()
                    .jwtToken(JwtToken.REFRESH_TOKEN)
                    .id(member.getId())
                    .build();
            String refreshToken = (String) jwtTokenProvider.generateIdentificationInfo(refreshTokenVO);

            redisService.setCache(accessToken, refreshToken);

            LoginSuccessDto login = LoginSuccessDto.builder()
                    .id(member.getId())
                    .name(member.getName())
                    .tel(member.getTel())
                    .addr(member.getAddr())
                    .addrDetail(member.getAddrDetail())
                    .zipCode(member.getZipCode())
                    .authority(member.getAuthority())
                    .accessToken(accessToken)
                    .refreshToken(refreshToken)
                    .build();

            member.loginSuccess();
            memberService.save(member);
            return login;
        } else {
            log.info("Login Fail");
            member.loginFailed();
            memberService.save(member);
            throw new CommerceException(ExceptionStatus.LOGIN_PASSWORD_FAIL);
        }
    }

    @Transactional(readOnly = true)
    @Override
    public String tokenReIssue(String accessToken, String refreshToken) {
        String redisRefreshToken = redisService.getCache(this.resolveAccessToken(accessToken));

        if (redisRefreshToken == null || !redisRefreshToken.equals(redisRefreshToken))
            throw new CommerceException(ExceptionStatus.AUTH_TOKEN_UN_MATCH);

        Claims tokenForSubject = jwtTokenProvider.getTokenForSubject(refreshToken);
        String subject = tokenForSubject.getSubject();

        IdentificationGenerateVO accessTokenVO = JwtIdentificationGenerateVO.builder()
                .jwtToken(JwtToken.ACCESS_TOKEN)
                .id(subject)
                .build();
        String reIssueAccessToken = (String) jwtTokenProvider.generateIdentificationInfo(accessTokenVO);

        redisService.deleteCache(accessToken);
        redisService.setCache(reIssueAccessToken, refreshToken);

        return reIssueAccessToken;
    }

    private String resolveAccessToken(String authorization) {
        return authorization.substring("Bearer".length() + 1);
    }
}
