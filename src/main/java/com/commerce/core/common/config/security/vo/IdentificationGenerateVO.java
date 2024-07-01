package com.commerce.core.common.config.security.vo;

public interface IdentificationGenerateVO {
    String getName();
    Authority getAuthority();
    JwtToken getJwtToken();
}
