package com.commerce.core.common.config.security.type;

import lombok.Builder;
import lombok.Getter;

@Builder
public record JwtIdentificationGenerateRequest(
        String id,
        Authority authority,
        JwtToken jwtToken
) implements IdentificationGenerateRequest {

    @Override
    public String getId() {
        return this.id;
    }

    @Override
    public Authority getAuthority() {
        return this.authority;
    }

    @Override
    public JwtToken getJwtToken() {
        return this.jwtToken;
    }
}
