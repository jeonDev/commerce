package com.commerce.core.member.facade;

import com.commerce.core.member.domain.entity.Member;
import com.commerce.core.member.service.LoginService;
import com.commerce.core.member.service.OAuthService;
import com.commerce.core.member.service.response.LoginServiceResponse;
import com.commerce.core.member.type.oauth.OAuthUserInfoResponse;
import org.springframework.stereotype.Component;

@Component
public class OAuthFacade {

    private final OAuthService oAuthService;
    private final LoginService loginService;

    public OAuthFacade(OAuthService oAuthService,
                       LoginService loginService
    ) {
        this.oAuthService = oAuthService;
        this.loginService = loginService;
    }

    public String getPage(String type) {
        return oAuthService.getPage(type);
    }

    public LoginServiceResponse getAccessToken(String type, String code) {
        Member member = oAuthService.getAccessToken(type, code);

        return loginService.login(member.getId(), null);
    }

    public OAuthUserInfoResponse getUserInfo(String type, String authorization) {
        return oAuthService.getUserInfo(type, authorization);
    }
}
