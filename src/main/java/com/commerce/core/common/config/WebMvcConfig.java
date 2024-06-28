package com.commerce.core.common.config;

import com.commerce.core.common.filter.JwtFilter;
import com.commerce.core.common.filter.LoggingFilter;
import com.commerce.core.common.security.IdentifierProvider;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("*");
    }

    @Bean
    public FilterRegistrationBean<LoggingFilter> loggingFilter() {
        return new FilterRegistrationBean<>(new LoggingFilter());
    }
}
