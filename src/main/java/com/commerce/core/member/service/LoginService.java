package com.commerce.core.member.service;

import com.commerce.core.common.service.CacheService;
import com.commerce.core.common.exception.CommerceException;
import com.commerce.core.common.exception.ExceptionStatus;
import com.commerce.core.common.config.security.IdentifierProvider;
import com.commerce.core.common.config.security.type.JwtIdentificationGenerateRequest;
import com.commerce.core.common.config.security.type.JwtToken;
import com.commerce.core.member.domain.MemberDao;
import com.commerce.core.member.service.request.LoginServiceRequest;
import com.commerce.core.member.service.response.LoginServiceResponse;
import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
public class LoginService {

    private final Long MAX_PASSWORD_WRONG_COUNT = 5L;
    private final MemberDao memberDao;
    private final IdentifierProvider jwtTokenProvider;
    private final PasswordEncoder passwordEncoder;
    private final CacheService<String, String> redisService;

    public LoginService(MemberDao memberDao,
                        IdentifierProvider jwtTokenProvider,
                        PasswordEncoder passwordEncoder,
                        CacheService<String, String> redisService
    ) {
        this.memberDao = memberDao;
        this.jwtTokenProvider = jwtTokenProvider;
        this.passwordEncoder = passwordEncoder;
        this.redisService = redisService;
    }

    @Transactional(noRollbackFor = CommerceException.class)
    public LoginServiceResponse login(LoginServiceRequest request) {
        String id = request.id();

        var member = memberDao.findByUsingId(id)
                .orElseThrow(() -> new CommerceException(ExceptionStatus.LOGIN_NOT_EXISTS_ID));

        // passwordFailCount >= 5
        if (member.getPasswordFailCount() >= MAX_PASSWORD_WRONG_COUNT) {
            throw new CommerceException(ExceptionStatus.LOGIN_NOT_EXISTS_ID);
        }

        // Login Success
        if (member.getOauthType() != null || passwordEncoder.matches(request.password(), member.getPassword())) {
            log.info("Login Success");
            var accessTokenVO = JwtIdentificationGenerateRequest.builder()
                    .jwtToken(JwtToken.ACCESS_TOKEN)
                    .id(member.getId())
                    .build();
            String accessToken = (String) jwtTokenProvider.generateIdentificationInfo(accessTokenVO);

            var refreshTokenVO = JwtIdentificationGenerateRequest.builder()
                    .jwtToken(JwtToken.REFRESH_TOKEN)
                    .id(member.getId())
                    .build();
            String refreshToken = (String) jwtTokenProvider.generateIdentificationInfo(refreshTokenVO);

            redisService.setCache(accessToken, refreshToken);

            var login = LoginServiceResponse.builder()
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
            memberDao.save(member);
            return login;
        } else {
            log.info("Login Fail");
            member.loginFailed();
            memberDao.save(member);
            throw new CommerceException(ExceptionStatus.LOGIN_PASSWORD_FAIL);
        }
    }

    @Transactional(readOnly = true)
    public String tokenReIssue(String accessToken, String refreshToken) {
        try {
            String redisRefreshToken = redisService.getCache(this.resolveAccessToken(accessToken));

            if (redisRefreshToken == null || !redisRefreshToken.equals(refreshToken))
                throw new CommerceException(ExceptionStatus.AUTH_TOKEN_UN_MATCH);

            Claims tokenForSubject = jwtTokenProvider.getTokenForSubject(refreshToken);
            String subject = tokenForSubject.getSubject();

            var accessTokenVO = JwtIdentificationGenerateRequest.builder()
                    .jwtToken(JwtToken.ACCESS_TOKEN)
                    .id(subject)
                    .build();
            String reIssueAccessToken = (String) jwtTokenProvider.generateIdentificationInfo(accessTokenVO);

            redisService.deleteCache(accessToken);
            redisService.setCache(reIssueAccessToken, refreshToken);
            return reIssueAccessToken;

        } catch (Exception e) {
            log.error("Refresh Token 발급 실패 : {}", e.getMessage());
            redisService.deleteCache(accessToken);
            throw new CommerceException(ExceptionStatus.AUTH_REFRESH_TOKEN_FAIL);
        }
    }

    private String resolveAccessToken(String authorization) {
        return authorization.substring("Bearer".length() + 1);
    }
}
