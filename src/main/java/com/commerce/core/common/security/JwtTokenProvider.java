package com.commerce.core.common.security;

import com.commerce.core.common.exception.CommerceException;
import com.commerce.core.common.exception.ExceptionStatus;
import com.commerce.core.common.security.vo.*;
import com.commerce.core.member.entity.Member;
import com.commerce.core.member.service.MemberService;
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

    private final MemberService memberService;

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
        Claims body = this.getTokenForSubject((String) identificationInfo);
        Member member = memberService.selectUseMember(body.getSubject())
                .orElseThrow(() -> new CommerceException(ExceptionStatus.AUTH_UNAUTHORIZED));
        return JwtAuthentication.builder()
                .id(member.getId())
                .name(member.getName())
                .authority(member.getAuthority())
                .build();
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
