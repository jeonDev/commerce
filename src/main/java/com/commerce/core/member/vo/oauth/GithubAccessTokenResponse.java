package com.commerce.core.member.vo.oauth;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public class GithubAccessTokenResponse {
    @JsonProperty("access_token")
    private String accessToken;
    @JsonProperty("token_type")
    private String tokenType;
    @JsonProperty("scope")
    private String scope;

    public OAuthTokenResponse toResponse() {
        return OAuthTokenResponse.builder()
                .accessToken(accessToken)
                .type(tokenType)
                .scope(scope)
                .oAuthType(OAuthType.GITHUB)
                .build();
    }
}