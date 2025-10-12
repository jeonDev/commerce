package com.commerce.core.api;

import com.commerce.core.api.request.LoginRequest;
import com.commerce.core.api.request.MemberRequest;
import com.commerce.core.api.request.MemberUpdateRequest;
import com.commerce.core.api.response.LoginResponse;
import com.commerce.core.api.response.MyInfoResponse;
import com.commerce.core.common.exception.CommerceException;
import com.commerce.core.common.exception.ExceptionStatus;
import com.commerce.core.common.utils.SessionUtils;
import com.commerce.core.common.type.HttpResponse;
import com.commerce.core.member.facade.MemberFacade;
import com.commerce.core.member.facade.MemberQueryFacade;
import com.commerce.core.member.type.oauth.OAuthUserInfoResponse;
import com.commerce.core.member.facade.LoginFacade;
import com.commerce.core.member.facade.OAuthFacade;
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

    private final MemberFacade memberFacade;
    private final MemberQueryFacade memberQueryFacade;
    private final LoginFacade loginFacade;
    private final OAuthFacade oAuthUseCase;

    @PostMapping("/signup")
    @Operation(summary = "회원가입", description = "회원가입을 진행한다.")
    public HttpResponse<Object> signup(@Valid @RequestBody MemberRequest request) {
        memberFacade.create(request.toRequest());
        return HttpResponse.builder()
                .build();
    }

    @PostMapping("/login")
    @Operation(summary = "로그인", description = "로그인을 처리한다.")
    public HttpResponse<LoginResponse> login(@Valid @RequestBody LoginRequest request,
                                             HttpServletResponse res) {
        LoginResponse response = LoginResponse.from(loginFacade.login(request.id(), request.password()));
        res.addCookie(this.createCookie(response.refreshToken()));
        return HttpResponse.<LoginResponse>builder()
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
    public HttpResponse<String> login(@RequestHeader("Authorization") String accessToken,
                                      @CookieValue("refreshToken") String refreshToken,
                                      HttpServletRequest request,
                                      HttpServletResponse response) {
        try {
            String token = loginFacade.tokenReIssue(accessToken, refreshToken);
            return HttpResponse.<String>builder()
                    .data(token)
                    .build();
        } catch (CommerceException e)  {
            if (ExceptionStatus.AUTH_REFRESH_TOKEN_FAIL.getCode().equals(e.getCode())) {
                Cookie cookie = this.resetCookieMake(request, "refreshToken");
                response.addCookie(cookie);
            }
        }
        return HttpResponse.<String>builder()
                .code(ExceptionStatus.AUTH_REFRESH_TOKEN_FAIL.getCode())
                .message(ExceptionStatus.AUTH_REFRESH_TOKEN_FAIL.getMessage())
                .build();
    }

    @GetMapping("/v1/myInfo")
    @Operation(summary = "내 정보 조회", description = "개인 정보 내역 조회")
    public HttpResponse<MyInfoResponse> myUserInfo() {
        return HttpResponse.<MyInfoResponse>builder()
                .data(MyInfoResponse.from(
                        memberQueryFacade.selectMyInfo(SessionUtils.getMemberSeq())
                ))
                .build();
    }

    @PutMapping("/v1/member/update")
    @Operation(summary = "내 정보 수정", description = "개인 정보 내역 수정")
    public HttpResponse<?> updateUserInfo(@RequestBody MemberUpdateRequest request) {
        memberFacade.update(request.toRequest(), SessionUtils.getMemberSeq());
        return HttpResponse.builder()
                .build();
    }

    @GetMapping("/oauth/login")
    @Operation(summary = "OAuth 로그인 페이지 가져오기")
    public HttpResponse<String> getOAuthPage(@RequestParam("type") String type) {
        String result = oAuthUseCase.getPage(type);
        return HttpResponse.<String>builder()
                .data(result)
                .build();
    }

    @GetMapping("/oauth/github/callback")
    @Operation(summary = "Github OAuth Login Callback")
    public HttpResponse<LoginResponse> githubCallback(@RequestParam("code") String code,
                                                      HttpServletResponse res) {
        LoginResponse response = LoginResponse.from(oAuthUseCase.getAccessToken("GITHUB", code));
        res.addCookie(this.createCookie(response.refreshToken()));
        return HttpResponse.<LoginResponse>builder()
                .data(response)
                .build();
    }

    @GetMapping("/oauth/user")
    @Operation(summary = "OAuth User Info")
    public HttpResponse<OAuthUserInfoResponse> getUserInfo(@RequestParam("type") String type,
                                                           @RequestHeader(value = "Authorization", required = false) String authorization) {
        OAuthUserInfoResponse userInfo = oAuthUseCase.getUserInfo(type, authorization);
        return HttpResponse.<OAuthUserInfoResponse>builder()
                .data(userInfo)
                .build();
    }

}
