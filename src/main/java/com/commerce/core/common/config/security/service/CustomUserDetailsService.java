package com.commerce.core.common.config.security.service;

import com.commerce.core.common.exception.CommerceException;
import com.commerce.core.common.exception.ExceptionStatus;
import com.commerce.core.common.config.security.type.CustomUserDetails;
import com.commerce.core.member.domain.entity.Member;
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

        return CustomUserDetails.builder()
                .memberSeq(member.getMemberSeq())
                .authority(member.getAuthority())
                .build();
    }
}
