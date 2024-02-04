package com.commerce.core.member.service;

import com.commerce.core.common.utils.EncryptUtils;
import com.commerce.core.member.entity.Member;
import com.commerce.core.member.repository.MemberRepository;
import com.commerce.core.member.vo.MemberDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Service
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;

    @Override
    public Member createMember(MemberDto dto) {
        Member member = dto.dtoToEntity();
        member.passwordEncrypt(EncryptUtils.encryptSHA256(dto.getPassword()));
        return memberRepository.save(member);
    }

    @Override
    public Optional<Member> selectMember(Long memberSeq) {
        return memberRepository.findById(memberSeq);
    }

    @Override
    public Optional<Member> selectUseMember(Long memberSeq) {
        return memberRepository.findByMemberSeqAndUseYn(memberSeq, "Y");
    }
}
