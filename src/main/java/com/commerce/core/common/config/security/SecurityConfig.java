package com.commerce.core.common.config.security;

import com.commerce.core.common.config.security.filter.JwtFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@RequiredArgsConstructor
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final JwtTokenProvider jwtTokenProvider;
    private final AuthenticationEntryPoint authenticationEntryPoint;
    private final AccessDeniedHandler accessDeniedHandler;

    @Value("${cors.domain}")
    private List<String> corsDomains;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable)
                .cors(corsConfigurer ->  corsConfigurer
                        .configurationSource(corsConfigurationSource())
                )
                .headers(h -> h.frameOptions(HeadersConfigurer.FrameOptionsConfig::disable))
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/v1/**")
                        .hasRole("USER")
                        .requestMatchers("/admin/**")
                        .hasRole("ADMIN")
                        .requestMatchers("/login"
                                , "/signup"
                                , "/tokenReIssue"
                                , "/swagger-ui/**"
                                , "/v3/api-docs/**")
                        .permitAll()
                )
                .exceptionHandling(httpSecurityExceptionHandlingConfigurer -> httpSecurityExceptionHandlingConfigurer
                        .accessDeniedHandler(accessDeniedHandler)
                        .authenticationEntryPoint(authenticationEntryPoint)
                )
                .addFilterBefore(new JwtFilter(jwtTokenProvider), UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

    /**
     * Cors Configuration
     * */
    private CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration corsConfigurationSource = new CorsConfiguration();

        corsConfigurationSource.setAllowedOriginPatterns(corsDomains);
        corsConfigurationSource.setAllowedMethods(List.of("HEAD","POST","GET","DELETE","PUT"));
        corsConfigurationSource.setAllowedHeaders(List.of("*"));
        corsConfigurationSource.setAllowCredentials(true);
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", corsConfigurationSource);
        return source;
    }
}
