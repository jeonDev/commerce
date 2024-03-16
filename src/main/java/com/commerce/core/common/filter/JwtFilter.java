package com.commerce.core.common.filter;

import com.commerce.core.common.security.IdentifierProvider;
import com.commerce.core.common.security.holder.AuthenticationHolderFactory;
import com.commerce.core.common.security.vo.AuthenticationInfo;
import com.commerce.core.common.utils.StringUtils;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {

    private final String BEARER_TYPE = "Bearer";

    private final IdentifierProvider jwtTokenProvider;
    
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        // Temp
        String uri = request.getRequestURI();
        if("/v1/member/login".equals(uri) || "/v1/member/signup".equals(uri)) {
            filterChain.doFilter(request, response);
            return;
        }
        // Preflight Request 방지
        if("OPTIONS".equals(request.getMethod())) {
            filterChain.doFilter(request, response);
            return;
        }
        String accessToken = this.resolveAccessToken(request);
        if(!StringUtils.isNullOrEmpty(accessToken) && jwtTokenProvider.validCheck(accessToken)) {
            AuthenticationInfo authenticationInfo = jwtTokenProvider.getAuthenticationInfo(accessToken);
            AuthenticationHolderFactory.getContext().set(authenticationInfo);
        } else {
            log.error("401");
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            return;
        }
        filterChain.doFilter(request, response);
    }

    private String resolveAccessToken(HttpServletRequest request) {
        String authorization = request.getHeader("Authorization");
        if(authorization == null || !authorization.startsWith(BEARER_TYPE)) return null;

        return authorization.substring(BEARER_TYPE.length() + 1);
    }
}
