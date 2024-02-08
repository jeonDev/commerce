package com.commerce.core.api;

import com.commerce.core.common.vo.ResponseVO;
import com.commerce.core.member.service.LoginService;
import com.commerce.core.member.service.MemberService;
import com.commerce.core.member.vo.LoginDto;
import com.commerce.core.member.vo.MemberDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/v1/member")
@RestController
public class MemberController {

    private final MemberService memberService;
    private final LoginService loginService;

    @PostMapping("/signup")
    public ResponseVO<Object> signup(@RequestBody MemberDto dto) {
        memberService.createMember(dto);
        return ResponseVO.builder()
                .build();
    }

    @PostMapping("/login")
    public ResponseVO<Object> login(@RequestBody LoginDto dto) {
        loginService.login(dto);
        return ResponseVO.builder()
                .build();
    }
}
