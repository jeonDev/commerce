package com.commerce.core.member.service;

import com.commerce.core.member.domain.MemberDao;
import com.commerce.core.member.domain.entity.Member;
import com.commerce.core.member.service.request.MemberServiceRequest;
import com.commerce.core.member.service.request.MemberUpdateServiceRequest;
import com.commerce.core.member.service.response.MyPageInfoServiceResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Service
public class MemberServiceImpl implements MemberService {

    private final MemberDao memberDao;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    @Override
    public Member createMember(MemberServiceRequest request) {
        Member member = request.toEntity();

        String encryptPassword = member.getOauthType() != null ? member.getPassword() : passwordEncoder.encode(member.getPassword());
        member.setEncryptPassword(encryptPassword);

        return memberDao.save(member);
    }

    @Transactional(readOnly = true)
    @Override
    public Optional<Member> selectMember(Long memberSeq) {
        return memberDao.findById(memberSeq);
    }

    @Transactional(readOnly = true)
    @Override
    public Optional<Member> selectUseMember(Long memberSeq) {
        return memberDao.findByUsingMemberSeq(memberSeq);
    }

    @Transactional(readOnly = true)
    @Override
    public Optional<Member> selectUseMember(String id) {
        return memberDao.findByUsingId(id);
    }

    @Transactional
    @Override
    public Member save(Member member) {
        return memberDao.save(member);
    }

    @Transactional(readOnly = true)
    @Override
    public MyPageInfoServiceResponse selectMyInfo(Long memberSeq) {
        return MyPageInfoServiceResponse.from(
                memberDao.selectMemberInfo(memberSeq)
        );
    }

    @Transactional
    @Override
    public void updateMember(MemberUpdateServiceRequest request, Long memberSeq) {
        memberDao.save(this.selectMember(memberSeq)
                .orElseThrow()
                .updateMyPageInfo(request)
        );
    }
}
