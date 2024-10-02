package com.commerce.core.member.service;

import com.commerce.core.member.vo.oauth.OAuthTokenResponse;

public interface OAuthService {

    String getPage(String type);
    OAuthTokenResponse getAccessToken(String type, String code);
}
