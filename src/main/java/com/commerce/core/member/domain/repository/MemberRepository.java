package com.commerce.core.member.domain.repository;

import com.commerce.core.member.domain.entity.Member;
import com.commerce.core.member.vo.oauth.OAuthType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {
    
    Optional<Member> findByMemberSeqAndUseYn(Long memberSeq, String useYn);
    Optional<Member> findByIdAndUseYn(String id, String useYn);
    Optional<Member> findByIdAndOauthType(String id, OAuthType oauthType);
}
