package com.commerce.core.common.security.holder;

import com.commerce.core.common.security.vo.AuthenticationInfo;

public interface AuthenticationHolder {
    void set(AuthenticationInfo authenticationInfo);
    AuthenticationInfo get();
}
