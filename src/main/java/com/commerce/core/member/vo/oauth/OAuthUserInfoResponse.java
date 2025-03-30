package com.commerce.core.member.vo.oauth;

import lombok.Builder;

@Builder
public record OAuthUserInfoResponse(
        String id,
        String name
) {

    public static OAuthUserInfoResponse from(GithubUserInfoResponse response) {
        return OAuthUserInfoResponse.builder()
                .id(response.getId())
                .name(response.getName())
                .build();
    }

}
