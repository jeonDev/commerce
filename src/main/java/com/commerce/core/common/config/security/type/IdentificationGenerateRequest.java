package com.commerce.core.common.config.security.type;

public interface IdentificationGenerateRequest {
    String getId();
    Authority getAuthority();
    JwtToken getJwtToken();
}
