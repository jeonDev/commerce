package com.commerce.core.common.security.holder;

import com.commerce.core.common.security.vo.Authority;

public class AuthorityHolderFactory {
    private AuthorityHolder authorityHolder;

    private AuthMapping[] authMappings;

    private AuthorityHolderFactory() {
    }

    public static AuthorityHolderFactory create() {
        return new AuthorityHolderFactory();
    }

    public AuthMapping antMatchers(String... urls) {
        AuthMapping from = AuthMapping.from(urls);
        return from;
    }

    public AuthorityHolder build() {
        return this.authorityHolder;
    }


    public static class AuthMapping {
        private String[] url;
        private Authority[] authority;

        public static AuthMapping from(String... url) {
            AuthMapping authMapping = new AuthMapping();
            authMapping.setUrl(url);
            return authMapping;
        }

        public AuthMapping setUrl(String... url) {
            this.url = url;
            return this;
        }
    }
}
