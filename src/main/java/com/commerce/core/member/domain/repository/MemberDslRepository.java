package com.commerce.core.member.domain.repository;

import com.commerce.core.member.domain.dto.MemberInfoDAO;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import static com.commerce.core.member.domain.entity.QMember.member;
import static com.commerce.core.point.domain.entity.QMemberPoint.memberPoint;

@Slf4j
@RequiredArgsConstructor
@Repository
public class MemberDslRepository {

    private final JPAQueryFactory dsl;

    public MemberInfoDAO selectMemberInfo(Long memberSeq) {
        return dsl.select(Projections.bean(MemberInfoDAO.class,
                                member.memberSeq,
                                member.id,
                                member.name,
                                member.tel,
                                member.addr,
                                member.addrDetail,
                                member.zipCode,
                                member.lastLoginDttm,
                                member.password,
                                member.useYn,
                                member.authority,
                                member.oauthType,
                                memberPoint.point.coalesce(0L).as("point")
                        )
                )
                .from(member)
                .leftJoin(member.memberPoint, memberPoint)
                .where(member.memberSeq.eq(memberSeq))
                .fetchOne();
    }
}
