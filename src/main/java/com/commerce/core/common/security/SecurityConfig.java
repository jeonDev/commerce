package com.commerce.core.common.security;

import com.commerce.core.common.security.holder.AuthorityHolder;
import com.commerce.core.common.security.holder.AuthorityHolderFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SecurityConfig {

    @Bean
    public AuthorityHolder authorityHolder() {
        return AuthorityHolderFactory.create()
                .build();
    }
}
