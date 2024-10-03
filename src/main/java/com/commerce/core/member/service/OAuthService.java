package com.commerce.core.member.service;

import com.commerce.core.member.vo.oauth.OAuthTokenResponse;
import com.commerce.core.member.vo.oauth.OAuthUserInfoResponse;

public interface OAuthService {

    String getPage(String type);
    OAuthTokenResponse getAccessToken(String type, String code);
    OAuthUserInfoResponse getUserInfo(String type, String authorization);

}
