package com.commerce.core.member.service;

import com.commerce.core.member.entity.Member;
import com.commerce.core.member.repository.MemberRepository;
import com.commerce.core.member.repository.dsl.MemberDslRepository;
import com.commerce.core.member.repository.dsl.vo.MemberInfoDAO;
import com.commerce.core.member.vo.MemberDto;
import com.commerce.core.member.vo.MyPageInfoDto;
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

    private final MemberRepository memberRepository;
    private final MemberDslRepository memberDslRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    @Override
    public Member createMember(MemberDto dto) {
        Member member = dto.dtoToEntity();
        String encPassword = passwordEncoder.encode(dto.getPassword());
        member.setEncryptPassword(encPassword);
        return memberRepository.save(member);
    }

    @Transactional(readOnly = true)
    @Override
    public Optional<Member> selectMember(Long memberSeq) {
        return memberRepository.findById(memberSeq);
    }

    @Transactional(readOnly = true)
    @Override
    public Optional<Member> selectUseMember(Long memberSeq) {
        return memberRepository.findByMemberSeqAndUseYn(memberSeq, "Y");
    }

    @Transactional(readOnly = true)
    @Override
    public Optional<Member> selectUseMember(String id) {
        return memberRepository.findByIdAndUseYn(id, "Y");
    }

    @Transactional
    @Override
    public Member save(Member member) {
        return memberRepository.save(member);
    }

    @Override
    public MyPageInfoDto selectMyInfo(Long memberSeq) {
        MemberInfoDAO dao = memberDslRepository.selectMemberInfo(memberSeq);
        return MyPageInfoDto.builder()
                .name(dao.getName())
                .point(dao.getPoint())
                .build();
    }
}
