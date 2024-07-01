package com.commerce.core.common.config.security;

import com.commerce.core.common.config.security.vo.IdentificationGenerateVO;
import com.commerce.core.common.config.security.vo.JwtToken;
import io.jsonwebtoken.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
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

    @Qualifier("redisTemplate")
    private RedisTemplate<String, String> redisTemplate;

    private final UserDetailsService userDetailsService;

    // TODO: 값 세팅 예정
    private String secretKey = "1111";

    @Override
    public String generateIdentificationInfo(IdentificationGenerateVO vo) {
        String token = this.jwtTokenGenerate(vo);

        if (vo.getJwtToken() == JwtToken.REFRESH_TOKEN) {
//            redisTemplate.opsForValue().set();
        }

        return token;
    }

    private String jwtTokenGenerate(IdentificationGenerateVO jwtIdentificationGenerateVO) {
        Claims claims = Jwts.claims().setSubject(jwtIdentificationGenerateVO.getName());
        Date now = new Date();
        Date expireDate = new Date(now.getTime() + jwtIdentificationGenerateVO.getJwtToken().getExpiredTime());

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(expireDate)
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
    }

    @Override
    public Authentication getAuthenticationInfo(Object identificationInfo) {
        Claims body = this.getTokenForSubject((String) identificationInfo);
        UserDetails userDetails = userDetailsService.loadUserByUsername(body.getSubject());

        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

    @Override
    public boolean validCheck(Object identificationInfo) {
        try{
            Jwts.parser().setSigningKey(secretKey).parseClaimsJws((String) identificationInfo);
            return true;
        } catch(ExpiredJwtException e) {
            log.error("", e);
        } catch(JwtException e) {
            log.error("", e);
        }
        return false;
    }

    private Claims getTokenForSubject(String token) {
        return Jwts.parser()
                .setSigningKey(secretKey)
                .parseClaimsJws(token)
                .getBody();
    }
}
