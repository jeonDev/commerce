package com.commerce.core.member.type.oauth;

import com.fasterxml.jackson.annotation.JsonProperty;

public record GithubAccessTokenResponse(
        @JsonProperty("access_token") String accessToken,
        @JsonProperty("token_type") String tokenType,
        @JsonProperty("scope") String scope
) {

}
