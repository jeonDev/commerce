package com.commerce.core.member.type.oauth;

import lombok.Builder;

@Builder
public record OAuthUserInfoResponse(
        String id,
        String name
) {

    public static OAuthUserInfoResponse from(GithubUserInfoResponse response) {
        return OAuthUserInfoResponse.builder()
                .id(response.id())
                .name(response.name())
                .build();
    }

}
