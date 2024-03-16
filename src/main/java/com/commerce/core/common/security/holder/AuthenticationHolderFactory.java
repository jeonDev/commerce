package com.commerce.core.common.security.holder;

public class AuthenticationHolderFactory {
    private static AuthenticationHolder authenticationHolder;

    static {
        authenticationHolder = new SecurityHolder();
    }

    public static AuthenticationHolder getContext() {
        return authenticationHolder;
    }
}
