package com.commerce.core.member.service;

public interface OAuthService {

    String getPage(String type);
    String getAccessToken(String code);
}
