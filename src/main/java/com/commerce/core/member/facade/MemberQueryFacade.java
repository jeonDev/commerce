package com.commerce.core.member.facade;

import com.commerce.core.member.domain.MemberDao;
import com.commerce.core.member.service.response.MyPageInfoServiceResponse;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class MemberQueryFacade {

    private final MemberDao memberDao;

    public MemberQueryFacade(MemberDao memberDao) {
        this.memberDao = memberDao;
    }

    @Transactional(readOnly = true)
    public MyPageInfoServiceResponse selectMyInfo(Long memberSeq) {
        return MyPageInfoServiceResponse.from(
                memberDao.selectMemberInfo(memberSeq)
        );
    }
}
