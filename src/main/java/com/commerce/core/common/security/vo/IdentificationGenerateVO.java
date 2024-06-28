package com.commerce.core.common.security.vo;

public interface IdentificationGenerateVO {
    String getName();
    Authority getAuthority();
    JwtToken getJwtToken();
}
