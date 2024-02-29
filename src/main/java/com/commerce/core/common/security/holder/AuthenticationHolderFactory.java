package com.commerce.core.common.security.holder;

public class AuthenticationHolderFactory {
    private static AuthenticationHolder authenticationHolder;

    {
        authenticationHolder = new SecurityHolder();
    }

    public static AuthenticationHolder getContext() {
        return authenticationHolder;
    }
}
