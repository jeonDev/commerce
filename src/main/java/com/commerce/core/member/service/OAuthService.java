package com.commerce.core.member.service;

import com.commerce.core.common.exception.CommerceException;
import com.commerce.core.common.exception.ExceptionStatus;
import com.commerce.core.common.properties.GithubKeyProperties;
import com.commerce.core.member.service.response.OAuthMemberResponse;
import com.commerce.core.member.type.oauth.*;
import com.commerce.core.member.external.OAuthApiClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class OAuthService {

    private final GithubKeyProperties githubKeyProperties;
    private final OAuthApiClient githubOAuthClient;

    public OAuthService(GithubKeyProperties githubKeyProperties,
                        OAuthApiClient githubOAuthClient) {
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

    public OAuthMemberResponse getAccessToken(OAuthType oAuthType, String code) {
        return switch (oAuthType) {
            case GITHUB -> this.getGithubResponse(code);
            default -> throw new CommerceException(ExceptionStatus.VALID_ERROR);
        };
    }

    private OAuthMemberResponse getGithubResponse(String code) {
        GithubAccessTokenResponse githubAccessTokenResponse = this.githubGetAccessToken(code);
        OAuthUserInfoResponse githubUserInfo = this.getUserInfo("GITHUB", githubAccessTokenResponse.tokenType() + " " + githubAccessTokenResponse.accessToken());
        String githubId = githubUserInfo.id();
        return new OAuthMemberResponse(
                OAuthType.GITHUB + "_" + githubId,
                githubUserInfo.name()
        );
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
