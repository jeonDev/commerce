package com.commerce.core.member.service;

import com.commerce.core.common.exception.CommerceException;
import com.commerce.core.common.exception.ExceptionStatus;
import com.commerce.core.member.entity.Member;
import com.commerce.core.member.repository.MemberRepository;
import com.commerce.core.member.vo.LoginDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class LoginServiceImpl implements LoginService {

    private final MemberRepository memberRepository;

    @Override
    public void login(LoginDto dto) {
        String id = dto.getId();
        String encPassword = dto.getPassword(); // TODO: 암호화

        Member member = memberRepository.findById(id)
                .orElseThrow(() -> new CommerceException(ExceptionStatus.ENTITY_IS_EMPTY));

        // Login Success
        if(member.getPassword() != null && member.getPassword().equals(encPassword)) {

        }

        throw new CommerceException(ExceptionStatus.LOGIN_FAIL);
    }
}
