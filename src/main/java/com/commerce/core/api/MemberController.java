package com.commerce.core.api;

import com.commerce.core.common.vo.ResponseVO;
import com.commerce.core.member.service.LoginService;
import com.commerce.core.member.service.MemberService;
import com.commerce.core.member.vo.LoginDto;
import com.commerce.core.member.vo.LoginSuccessDto;
import com.commerce.core.member.vo.MemberDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Tag(name = "사용자 API")
@Slf4j
@RequiredArgsConstructor
@RestController
public class MemberController {

    private final MemberService memberService;
    private final LoginService loginService;

    @PostMapping("/v1/member/signup")
    @Operation(summary = "회원가입", description = "회원가입을 진행한다.")
    public ResponseVO<Object> signup(@RequestBody MemberDto dto) {
        memberService.createMember(dto);
        return ResponseVO.builder()
                .build();
    }

    @PostMapping("/login")
    @Operation(summary = "로그인", description = "로그인을 처리한다.")
    public ResponseVO<LoginSuccessDto> login(HttpServletResponse res,
                                             @RequestBody LoginDto dto) {
        LoginSuccessDto response = loginService.login(dto);
        res.addCookie(this.createCookie(response.getRefreshToken()));
        return ResponseVO.<LoginSuccessDto>builder()
                .data(response)
                .build();
    }

    private Cookie createCookie(String token) {
        Cookie cookie = new Cookie("refreshToken", token);
        cookie.setHttpOnly(true);
        cookie.setSecure(true);
        cookie.setPath("/");
        return cookie;
    }

    @PostMapping("/tokenReIssue")
    @Operation(summary = "토큰 재발급", description = "만료된 토큰을 재 발급한다.")
    public ResponseVO<String> login(@RequestHeader("token") String accessToken,
                                             @CookieValue("refreshToken") String refreshToken) {
        String token = loginService.tokenReIssue(accessToken, refreshToken);
        return ResponseVO.<String>builder()
                .data(token)
                .build();
    }

}
