package com.commerce.core.api;

import com.commerce.core.common.exception.CommerceException;
import com.commerce.core.common.exception.ExceptionStatus;
import com.commerce.core.common.utils.SessionUtils;
import com.commerce.core.common.vo.ResponseVO;
import com.commerce.core.member.service.LoginService;
import com.commerce.core.member.service.MemberService;
import com.commerce.core.member.service.OAuthService;
import com.commerce.core.member.vo.LoginDto;
import com.commerce.core.member.vo.LoginSuccessDto;
import com.commerce.core.member.vo.MemberDto;
import com.commerce.core.member.vo.MyPageInfoDto;
import com.commerce.core.member.vo.oauth.OAuthTokenResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
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
    private final OAuthService oAuthService;

    @PostMapping("/signup")
    @Operation(summary = "회원가입", description = "회원가입을 진행한다.")
    public ResponseVO<Object> signup(@Valid @RequestBody MemberDto dto) {
        memberService.createMember(dto);
        return ResponseVO.builder()
                .build();
    }

    @PostMapping("/login")
    @Operation(summary = "로그인", description = "로그인을 처리한다.")
    public ResponseVO<LoginSuccessDto> login(HttpServletResponse res,
                                             @Valid @RequestBody LoginDto dto) {
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

    private Cookie resetCookieMake(HttpServletRequest request, String cookieName) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookieName.equals(cookie.getName())) {
                    cookie.setValue("");
                    cookie.setPath("/");
                    cookie.setMaxAge(0);
                    return cookie;
                }
            }
        }
        throw new AssertionError();
    }

    @PostMapping("/tokenReIssue")
    @Operation(summary = "토큰 재발급", description = "만료된 토큰을 재 발급한다.")
    public ResponseVO<String> login(HttpServletRequest request,
                                    HttpServletResponse response,
                                    @RequestHeader("Authorization") String accessToken,
                                    @CookieValue("refreshToken") String refreshToken) {
        try {
            String token = loginService.tokenReIssue(accessToken, refreshToken);
            return ResponseVO.<String>builder()
                    .data(token)
                    .build();
        } catch (CommerceException e)  {
            if (ExceptionStatus.AUTH_REFRESH_TOKEN_FAIL.getCode().equals(e.getCode())) {
                Cookie cookie = this.resetCookieMake(request, "refreshToken");
                response.addCookie(cookie);
            }
        }
        return ResponseVO.<String>builder()
                .code(ExceptionStatus.AUTH_REFRESH_TOKEN_FAIL.getCode())
                .message(ExceptionStatus.AUTH_REFRESH_TOKEN_FAIL.getMessage())
                .build();
    }

    @GetMapping("/v1/myInfo")
    @Operation(summary = "내 정보 조회", description = "개인 정보 내역 조회")
    public ResponseVO<MyPageInfoDto> myUserInfo() {
        MyPageInfoDto result = memberService.selectMyInfo(SessionUtils.getMemberSeq());
        return ResponseVO.<MyPageInfoDto>builder()
                .data(result)
                .build();
    }

    @PutMapping("/v1/member/update")
    @Operation(summary = "내 정보 수정", description = "개인 정보 내역 수정")
    public ResponseVO<?> updateUserInfo(@RequestBody MyPageInfoDto myPageInfoDto) {
        memberService.updateUserInfo(myPageInfoDto, SessionUtils.getMemberSeq());
        return ResponseVO.builder()
                .build();
    }

    @GetMapping("/oauth/login")
    @Operation(summary = "OAuth 로그인 페이지 가져오기")
    public ResponseVO<String> getOAuthPage(@RequestParam("type") String type) {
        String result = oAuthService.getPage(type);
        return ResponseVO.<String>builder()
                .data(result)
                .build();
    }

    @GetMapping("/oauth/github/callback")
    @Operation(summary = "Github OAuth Login Callback")
    public ResponseVO<OAuthTokenResponse> githubCallback(@RequestParam("code") String code) {
        OAuthTokenResponse response = oAuthService.getAccessToken("GITHUB", code);
        return ResponseVO.<OAuthTokenResponse>builder()
                .data(response)
                .build();
    }

}
