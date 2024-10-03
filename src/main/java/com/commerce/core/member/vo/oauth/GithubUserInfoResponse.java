package com.commerce.core.member.vo.oauth;

import lombok.Getter;

@Getter
public class GithubUserInfoResponse {
    private String id;
    private String name;

    public OAuthUserInfoResponse toResponse() {
        return OAuthUserInfoResponse.builder()
                .id(id)
                .name(name)
                .build();
    }
}
