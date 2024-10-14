package com.commerce.core.member.service;

import com.commerce.core.common.exception.CommerceException;
import com.commerce.core.common.exception.ExceptionStatus;
import com.commerce.core.common.properties.GithubKeyProperties;
import com.commerce.core.member.entity.Member;
import com.commerce.core.member.repository.MemberRepository;
import com.commerce.core.member.vo.LoginDto;
import com.commerce.core.member.vo.LoginSuccessDto;
import com.commerce.core.member.vo.MemberDto;
import com.commerce.core.member.vo.oauth.*;
import com.commerce.core.request.OAuthApiClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
public class OAuthServiceImpl implements OAuthService {

    private final MemberRepository memberRepository;
    private final MemberService memberService;
    private final LoginService loginService;
    private final GithubKeyProperties githubKeyProperties;
    private final OAuthApiClient githubOAuthClient;

    public OAuthServiceImpl(MemberRepository memberRepository,
                            MemberService memberService,
                            LoginService loginService,
                            GithubKeyProperties githubKeyProperties,
                            OAuthApiClient githubOAuthClient) {
        this.memberRepository = memberRepository;
        this.memberService = memberService;
        this.loginService = loginService;
        this.githubKeyProperties = githubKeyProperties;
        this.githubOAuthClient = githubOAuthClient;
    }

    @Override
    public String getPage(String type) {
        OAuthType oAuthType = OAuthType.valueOf(type);

        switch (oAuthType) {
            case GITHUB -> {
                return this.githubGetPage();
            }
            default -> throw new CommerceException(ExceptionStatus.VALID_ERROR);
        }
    }

    @Override
    public LoginSuccessDto getAccessToken(String type, String code) {
        OAuthType oAuthType = OAuthType.valueOf(type);
        String id = null;
        String name = null;
        switch (oAuthType) {
            case GITHUB -> {
                GithubAccessTokenResponse githubAccessTokenResponse = this.githubGetAccessToken(code);
                OAuthUserInfoResponse githubUserInfo =
                        this.getUserInfo("GITHUB", githubAccessTokenResponse.getTokenType() + " " + githubAccessTokenResponse.getAccessToken());
                String githubId = githubUserInfo.getId();
                name = githubUserInfo.getName();
                id = oAuthType + "_" + githubId;
            }
            default -> throw new CommerceException(ExceptionStatus.VALID_ERROR);
        }

        Optional<Member> optionalMember = memberRepository.findByIdAndOauthType(id, oAuthType);
        LoginDto loginDto = new LoginDto();
        if (optionalMember.isPresent()) {
            // Token 발급
            Member member = optionalMember.get();
            loginDto.setId(member.getId());
        } else {
            // 회원가입 & Token 발급
            MemberDto memberDto = new MemberDto();
            memberDto.setId(id);
            memberDto.setName(name);
            memberDto.setOAuthType(oAuthType);
            Member member = memberService.createMember(memberDto);
            loginDto.setId(member.getId());
        }

        return loginService.login(loginDto);
    }

    @Override
    public OAuthUserInfoResponse getUserInfo(String type, String authorization) {
        OAuthType oAuthType = OAuthType.valueOf(type);
        switch (oAuthType) {
            case GITHUB -> {
                GithubUserInfoResponse githubUserInfoResponse = this.githubGetUserInfo(authorization);
                return githubUserInfoResponse.toResponse();
            }
            default -> throw new CommerceException(ExceptionStatus.VALID_ERROR);
        }
    }

    private String githubGetPage() {
        return githubKeyProperties.getLoginUrl() + "?client_id=" + githubKeyProperties.getClientId();
    }

    private GithubAccessTokenResponse githubGetAccessToken(String code) {
        GithubAccessTokenRequest request = GithubAccessTokenRequest.builder()
                .clientId(githubKeyProperties.getClientId())
                .clientSecret(githubKeyProperties.getClientSecret())
                .code(code)
                .build();
        return githubOAuthClient.getAccessTokenApiCall(githubKeyProperties.getOauthAccessTokenUrl(), request, GithubAccessTokenResponse.class);
    }

    private GithubUserInfoResponse githubGetUserInfo(String authorization) {
        return githubOAuthClient.getUserInfoApiCall(githubKeyProperties.getOauthApiUserUrl(), authorization, GithubUserInfoResponse.class);
    }

}
