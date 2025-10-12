package com.commerce.core.member.service;

import com.commerce.core.member.domain.MemberDao;
import com.commerce.core.member.domain.entity.Member;
import com.commerce.core.member.service.request.MemberServiceRequest;
import com.commerce.core.member.service.request.MemberUpdateServiceRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
public class MemberService {

    private final MemberDao memberDao;
    private final PasswordEncoder passwordEncoder;

    public MemberService(MemberDao memberDao,
                         PasswordEncoder passwordEncoder) {
        this.memberDao = memberDao;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public Member createMember(MemberServiceRequest request) {
        Member member = request.toEntity();

        String encryptPassword = member.getOauthType() != null ? member.getPassword() : passwordEncoder.encode(member.getPassword());
        member.setEncryptPassword(encryptPassword);

        return memberDao.save(member);
    }

    @Transactional
    public void updateMember(MemberUpdateServiceRequest request, Long memberSeq) {
        var member = memberDao.findById(memberSeq)
                .orElseThrow()
                .updateMyPageInfo(request);
        memberDao.save(member);
    }

}
