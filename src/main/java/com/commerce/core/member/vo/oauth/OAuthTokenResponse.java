package com.commerce.core.member.vo.oauth;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class OAuthTokenResponse {
    private String accessToken;
    private String type;
    private String scope;
    private OAuthType oAuthType;
}
