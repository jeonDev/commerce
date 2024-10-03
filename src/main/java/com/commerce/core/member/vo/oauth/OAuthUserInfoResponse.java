package com.commerce.core.member.vo.oauth;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class OAuthUserInfoResponse {
    private final String id;
    private final String name;
}
