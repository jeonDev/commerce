package com.commerce.core.common.config;

import com.commerce.core.common.filter.LoggingFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Bean
    public FilterRegistrationBean<LoggingFilter> loggingFilter() {
        return new FilterRegistrationBean<>(new LoggingFilter());
    }
}
