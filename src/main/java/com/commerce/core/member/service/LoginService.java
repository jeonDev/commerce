package com.commerce.core.member.service;

import com.commerce.core.member.service.request.LoginServiceRequest;
import com.commerce.core.member.service.response.LoginServiceResponse;

public interface LoginService {

    /**
     * Login
     */
    LoginServiceResponse login(LoginServiceRequest request);

    /**
     * Token ReIssue
     */
    String tokenReIssue(String accessToken, String refreshToken);
}
