package com.commerce.core.common.config;

import com.commerce.core.common.filter.LoggingFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

    @Bean
    public FilterRegistrationBean<LoggingFilter> loggingFilter() {
        return new FilterRegistrationBean<>(new LoggingFilter());
    }
}
