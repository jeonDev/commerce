package com.commerce.core.common.security.vo;

public interface AuthenticationInfo {
    String getId();
    Authority getAuthority();
    String getName();
}
