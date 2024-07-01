package com.commerce.core.common.config.security.vo;

public interface AuthenticationInfo {
    String getId();
    Authority getAuthority();
    String getName();
}
