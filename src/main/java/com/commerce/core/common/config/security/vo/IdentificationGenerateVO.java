package com.commerce.core.common.config.security.vo;

public interface IdentificationGenerateVO {
    String getId();
    Authority getAuthority();
    JwtToken getJwtToken();
}
