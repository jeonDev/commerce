package com.commerce.core.member.service;

import com.commerce.core.common.exception.CommerceException;
import com.commerce.core.common.exception.ExceptionStatus;
import com.commerce.core.common.utils.EncryptUtils;
import com.commerce.core.member.entity.Member;
import com.commerce.core.member.repository.MemberRepository;
import com.commerce.core.member.vo.LoginDto;
import com.commerce.core.member.vo.LoginSuccessDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class LoginServiceImpl implements LoginService {

    private final Long MAX_PASSWORD_WRONG_COUNT = 5L;
    private final MemberRepository memberRepository;

    @Override
    public LoginSuccessDto login(LoginDto dto) {
        String id = dto.getId();
        String encPassword = EncryptUtils.encryptSHA256(dto.getPassword());

        Member member = memberRepository.findById(id)
                .orElseThrow(() -> new CommerceException(ExceptionStatus.ENTITY_IS_EMPTY));

        // passwordFailCount >= 5
        if(member.getPasswordFailCount() >= MAX_PASSWORD_WRONG_COUNT) {
            throw new CommerceException(ExceptionStatus.LOGIN_PASSWORD_FAIL_MAX_COUNT);
        }

        // Login Success
        if(member.getPassword().equals(encPassword)) {
            LoginSuccessDto login = LoginSuccessDto.builder()
                    .id(member.getId())
                    .name(member.getName())
                    .tel(member.getTel())
                    .addr(member.getAddr())
                    .addrDetail(member.getAddrDetail())
                    .zipCode(member.getZipCode())
                    .build();
            member.loginSuccess();
            memberRepository.save(member);
            return login;
        } else {
            member.loginFailed();
            memberRepository.save(member);
            throw new CommerceException(ExceptionStatus.LOGIN_PASSWORD_FAIL);
        }
    }
}
