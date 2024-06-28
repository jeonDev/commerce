package com.commerce.core.common.filter;

import com.commerce.core.common.security.IdentifierProvider;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {

    private final String BEARER_TYPE = "Bearer";

    private final IdentifierProvider jwtTokenProvider;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String accessToken = this.resolveAccessToken(request);
        if (accessToken != null) {
            if(jwtTokenProvider.validCheck(accessToken)) {
                Authentication authenticationInfo = jwtTokenProvider.getAuthenticationInfo(accessToken);
                SecurityContextHolder.getContext().setAuthentication(authenticationInfo);
            }
        }
        filterChain.doFilter(request, response);
    }

    private String resolveAccessToken(HttpServletRequest request) {
        String authorization = request.getHeader("Authorization");
        if(authorization == null || !authorization.startsWith(BEARER_TYPE)) return null;

        return authorization.substring(BEARER_TYPE.length() + 1);
    }
}
