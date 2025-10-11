package com.commerce.core.member.service;

import com.commerce.core.common.exception.CommerceException;
import com.commerce.core.common.exception.ExceptionStatus;
import com.commerce.core.common.properties.GithubKeyProperties;
import com.commerce.core.member.domain.MemberDao;
import com.commerce.core.member.domain.entity.Member;
import com.commerce.core.member.service.request.LoginServiceRequest;
import com.commerce.core.member.service.response.LoginServiceResponse;
import com.commerce.core.member.service.request.MemberServiceRequest;
import com.commerce.core.member.type.oauth.*;
import com.commerce.core.member.external.OAuthApiClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
public class OAuthService {

    private final MemberDao memberDao;
    private final MemberService memberService;
    private final LoginService loginService;
    private final GithubKeyProperties githubKeyProperties;
    private final OAuthApiClient githubOAuthClient;

    public OAuthService(MemberDao memberDao,
                        MemberService memberService,
                        LoginService loginService,
                        GithubKeyProperties githubKeyProperties,
                        OAuthApiClient githubOAuthClient) {
        this.memberDao = memberDao;
        this.memberService = memberService;
        this.loginService = loginService;
        this.githubKeyProperties = githubKeyProperties;
        this.githubOAuthClient = githubOAuthClient;
    }

    public String getPage(String type) {
        var oAuthType = OAuthType.valueOf(type);

        switch (oAuthType) {
            case GITHUB -> {
                return this.githubGetPage();
            }
            default -> throw new CommerceException(ExceptionStatus.VALID_ERROR);
        }
    }

    public LoginServiceResponse getAccessToken(String type, String code) {
        OAuthType oAuthType = OAuthType.valueOf(type);
        String id = null;
        String name = null;

        switch (oAuthType) {
            case GITHUB -> {
                GithubAccessTokenResponse githubAccessTokenResponse = this.githubGetAccessToken(code);
                OAuthUserInfoResponse githubUserInfo =
                        this.getUserInfo("GITHUB", githubAccessTokenResponse.tokenType() + " " + githubAccessTokenResponse.accessToken());
                String githubId = githubUserInfo.id();
                name = githubUserInfo.name();
                id = oAuthType + "_" + githubId;
            }
            default -> throw new CommerceException(ExceptionStatus.VALID_ERROR);
        }

        Optional<Member> optionalMember = memberDao.findByIdAndOauthType(id, oAuthType);
        Member member = optionalMember.isPresent()
                ? optionalMember.get()
                : this.oauthCreateMember(id, name, oAuthType);

        LoginServiceRequest request = LoginServiceRequest.builder()
                .id(member.getId())
                .build();

        return loginService.login(request);
    }

    private Member oauthCreateMember(String id, String name, OAuthType oAuthType) {
        var request = MemberServiceRequest.builder()
                .id(id)
                .name(name)
                .oAuthType(oAuthType)
                .build();
        return memberService.createMember(request);
    }

    public OAuthUserInfoResponse getUserInfo(String type, String authorization) {
        var oAuthType = OAuthType.valueOf(type);
        switch (oAuthType) {
            case GITHUB -> {
                return OAuthUserInfoResponse.from(
                        this.githubGetUserInfo(authorization)
                );
            }
            default -> throw new CommerceException(ExceptionStatus.VALID_ERROR);
        }
    }

    private String githubGetPage() {
        return githubKeyProperties.getLoginUrl() + "?client_id=" + githubKeyProperties.getClientId();
    }

    private GithubAccessTokenResponse githubGetAccessToken(String code) {
        var request = GithubAccessTokenRequest.builder()
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
