package com.commerce.core.common.config.security;

import com.commerce.core.common.config.security.type.IdentificationGenerateRequest;
import io.jsonwebtoken.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import java.util.Date;

@RequiredArgsConstructor
@Slf4j
@Component
public class JwtTokenProvider implements IdentifierProvider {

    private final UserDetailsService userDetailsService;

    @Value("${security.secret-key}")
    private String secretKey;

    @Override
    public String generateIdentificationInfo(IdentificationGenerateRequest vo) {
        return this.jwtTokenGenerate(vo);
    }

    private String jwtTokenGenerate(IdentificationGenerateRequest jwtIdentificationGenerateRequest) {
        Claims claims = Jwts.claims().setSubject(jwtIdentificationGenerateRequest.getId());
        Date now = new Date();
        Date expireDate = new Date(now.getTime() + jwtIdentificationGenerateRequest.getJwtToken().getExpiredTime());

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(expireDate)
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
    }

    @Override
    public Authentication getAuthenticationInfo(Object identificationInfo) {
        Claims body = getTokenForSubject((String) identificationInfo);
        UserDetails userDetails = userDetailsService.loadUserByUsername(body.getSubject());

        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

    @Override
    public boolean validCheck(Object identificationInfo) {
        try{
            Jwts.parser().setSigningKey(secretKey).parseClaimsJws((String) identificationInfo);
            return true;
        } catch(JwtException e) {
            log.debug("Jwt Exception : {}", e.getMessage());
        }
        return false;
    }

    @Override
    public Claims getTokenForSubject(String token) {
        return Jwts.parser()
                .setSigningKey(secretKey)
                .parseClaimsJws(token)
                .getBody();
    }
}
