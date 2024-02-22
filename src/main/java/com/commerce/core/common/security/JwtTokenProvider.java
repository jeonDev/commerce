package com.commerce.core.common.security;

import com.commerce.core.common.security.vo.AuthenticationInfo;
import com.commerce.core.common.security.vo.IdentificationVO;
import com.commerce.core.common.security.vo.JwtIdentificationVO;
import com.commerce.core.common.security.vo.JwtToken;
import io.jsonwebtoken.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.Date;

@RequiredArgsConstructor
@Slf4j
@Component
public class JwtTokenProvider implements IdentifierProvider {

    @Qualifier("redisTemplate")
    private RedisTemplate<String, String> redisTemplate;

    // TODO: 값 세팅 예정
    private String secretKey = "1111";

    @Override
    public String generateIdentificationInfo(IdentificationVO vo) {
        JwtIdentificationVO jwtIdentificationVO = (JwtIdentificationVO) vo;
        String token = jwtTokenGenerate(jwtIdentificationVO);

        if (jwtIdentificationVO.getJwtToken() == JwtToken.REFRESH_TOKEN) {
//            redisTemplate.opsForValue().set();
        }

        return token;
    }

    private String jwtTokenGenerate(JwtIdentificationVO jwtIdentificationVO) {
        Claims claims = Jwts.claims().setSubject(jwtIdentificationVO.getName());
        Date now = new Date();
        Date expireDate = new Date(now.getTime() + jwtIdentificationVO.getJwtToken().getExpiredTime());

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(expireDate)
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
    }

    @Override
    public AuthenticationInfo getAuthenticationInfo(Object identificationInfo) {
        return null;
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
}
