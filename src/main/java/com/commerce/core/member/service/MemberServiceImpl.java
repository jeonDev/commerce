package com.commerce.core.member.service;

import com.commerce.core.member.domain.MemberDao;
import com.commerce.core.member.domain.entity.Member;
import com.commerce.core.member.domain.dto.MemberInfoDAO;
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

    private final MemberDao memberDao;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    @Override
    public Member createMember(MemberDto dto) {
        Member member = dto.dtoToEntity();

        String encryptPassword = member.getOauthType() == null ? member.getPassword() : passwordEncoder.encode(member.getPassword());
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
    public MyPageInfoDto selectMyInfo(Long memberSeq) {
        MemberInfoDAO dao = memberDao.selectMemberInfo(memberSeq);
        return MyPageInfoDto.builder()
                .id(dao.getId())
                .name(dao.getName())
                .tel(dao.getTel())
                .addr(dao.getAddr())
                .addrDetail(dao.getAddrDetail())
                .zipCode(dao.getZipCode())
                .point(dao.getPoint())
                .build();
    }

    @Transactional
    @Override
    public void updateUserInfo(MyPageInfoDto myPageInfoDto, Long memberSeq) {
        Member member = this.selectMember(memberSeq)
                .orElseThrow();
        member.updateMyPageInfo(myPageInfoDto);
        memberDao.save(member);
    }
}
