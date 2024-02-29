package com.commerce.core.common.security.holder;

import com.commerce.core.common.security.vo.AuthenticationInfo;

public class SecurityHolder implements AuthenticationHolder {
    private final ThreadLocal<AuthenticationInfo> authenticationInfoThreadLocal = new ThreadLocal<>();

    public void set(AuthenticationInfo authenticationInfo) {
        authenticationInfoThreadLocal.set(authenticationInfo);
    }

    public AuthenticationInfo get() {
        return authenticationInfoThreadLocal.get();
    }
}
