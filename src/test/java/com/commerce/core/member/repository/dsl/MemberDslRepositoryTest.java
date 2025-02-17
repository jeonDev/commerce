package com.commerce.core.member.repository.dsl;

import com.commerce.core.member.domain.repository.MemberDslRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class MemberDslRepositoryTest {

    @Autowired
    MemberDslRepository memberDslRepository;

    @Test
    void name() {
        memberDslRepository.selectMemberInfo(1L);
    }
}