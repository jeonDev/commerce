package com.commerce.core.member.domain;

import com.commerce.core.member.domain.dto.MemberInfoDAO;
import com.commerce.core.member.domain.entity.Member;
import com.commerce.core.member.domain.repository.MemberDslRepository;
import com.commerce.core.member.domain.repository.MemberRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class MemberDaoImpl implements MemberDao {
    private final MemberDslRepository memberDslRepository;
    private final MemberRepository memberRepository;

    public MemberDaoImpl(MemberDslRepository memberDslRepository,
                         MemberRepository memberRepository) {
        this.memberDslRepository = memberDslRepository;
        this.memberRepository = memberRepository;
    }


    @Override
    public Member save(Member member) {
        return memberRepository.save(member);
    }

    @Override
    public Optional<Member> findById(Long id) {
        return memberRepository.findById(id);
    }

    @Override
    public Optional<Member> findByUsingMemberSeq(Long memberSeq) {
        return memberRepository.findByMemberSeqAndUseYn(memberSeq, "Y");
    }

    @Override
    public Optional<Member> findByUsingId(String id) {
        return memberRepository.findByIdAndUseYn(id, "Y");
    }

    @Override
    public MemberInfoDAO selectMemberInfo(Long memberSeq) {
        return memberDslRepository.selectMemberInfo(memberSeq);
    }
}
