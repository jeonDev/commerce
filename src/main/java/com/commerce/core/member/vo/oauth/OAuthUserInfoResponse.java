package com.commerce.core.member.vo.oauth;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
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
