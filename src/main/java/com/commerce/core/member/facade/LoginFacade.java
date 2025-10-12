package com.commerce.core.member.facade;

import com.commerce.core.member.service.LoginService;
import com.commerce.core.member.service.response.LoginServiceResponse;
import org.springframework.stereotype.Component;

@Component
public class LoginFacade {

    private final LoginService loginService;

    public LoginFacade(LoginService loginService) {
        this.loginService = loginService;
    }

    public LoginServiceResponse login(String id, String password) {
        return loginService.login(id, password);
    }

    public String tokenReIssue(String accessToken, String refreshToken) {
        return loginService.tokenReIssue(accessToken, refreshToken);
    }
}
