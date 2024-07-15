package com.commerce.core.member.repository.dsl;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class MemberDslRepositoryTest {

    @Autowired
    MemberDslRepository memberDslRepository;

    @Test
    void name() {
        memberDslRepository.selectMemberInfo(1L);
    }
}