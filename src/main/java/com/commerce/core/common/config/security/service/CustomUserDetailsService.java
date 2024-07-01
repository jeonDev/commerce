package com.commerce.core.common.config.security.service;

import com.commerce.core.common.exception.CommerceException;
import com.commerce.core.common.exception.ExceptionStatus;
import com.commerce.core.common.config.security.vo.CustomUserDetails;
import com.commerce.core.common.config.security.vo.JwtAuthentication;
import com.commerce.core.member.entity.Member;
import com.commerce.core.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final MemberService memberService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Member member = memberService.selectUseMember(username)
                .orElseThrow(() -> new CommerceException(ExceptionStatus.AUTH_UNAUTHORIZED));

        JwtAuthentication authentication = JwtAuthentication.builder()
                .id(member.getId())
                .name(member.getName())
                .authority(member.getAuthority())
                .build();

        return CustomUserDetails.builder()
                .authenticationInfo(authentication)
                .build();
    }
}
