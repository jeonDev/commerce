package com.commerce.core.member.type.oauth;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

@Builder
public record GithubAccessTokenRequest(
        @JsonProperty("client_id") String clientId,
        @JsonProperty("client_secret") String clientSecret,
        @JsonProperty("code") String code,
        @JsonProperty("redirect_uri") String redirectUri
) {

}
