package com.commerce.core.member.service;

import com.commerce.core.member.service.response.LoginServiceResponse;
import com.commerce.core.member.type.oauth.OAuthUserInfoResponse;

public interface OAuthService {

    String getPage(String type);
    LoginServiceResponse getAccessToken(String type, String code);
    OAuthUserInfoResponse getUserInfo(String type, String authorization);

}
