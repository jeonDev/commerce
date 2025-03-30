package com.commerce.core.common.config.security.type;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public record JwtIdentificationGenerateRequest(
        String id,
        Authority authority,
        JwtToken jwtToken
) implements IdentificationGenerateRequest {

}
