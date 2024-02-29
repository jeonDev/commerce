package com.commerce.core.common.filter;

import com.commerce.core.common.security.IdentifierProvider;
import com.commerce.core.common.security.holder.AuthenticationHolderFactory;
import com.commerce.core.common.security.vo.AuthenticationInfo;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {

    private final IdentifierProvider jwtTokenProvider;
    
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String token = "";
        AuthenticationInfo authenticationInfo = jwtTokenProvider.getAuthenticationInfo(token);
        AuthenticationHolderFactory.getContext().set(authenticationInfo);
    }
}
