package com.commerce.core.api;

import com.commerce.core.common.vo.ResponseVO;
import com.commerce.core.member.service.LoginService;
import com.commerce.core.member.service.MemberService;
import com.commerce.core.member.vo.LoginDto;
import com.commerce.core.member.vo.MemberDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "사용자 API")
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/v1/member")
@RestController
public class MemberController {

    private final MemberService memberService;
    private final LoginService loginService;

    @PostMapping("/signup")
    @Operation(summary = "회원가입", description = "회원가입을 진행한다.")
    public ResponseVO<Object> signup(@RequestBody MemberDto dto) {
        memberService.createMember(dto);
        return ResponseVO.builder()
                .build();
    }

    @PostMapping("/login")
    @Operation(summary = "로그인", description = "로그인을 처리한다.")
    public ResponseVO<Object> login(@RequestBody LoginDto dto) {
        loginService.login(dto);
        return ResponseVO.builder()
                .build();
    }
}
