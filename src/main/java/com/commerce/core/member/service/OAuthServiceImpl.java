package com.commerce.core.member.service;

import com.commerce.core.common.exception.CommerceException;
import com.commerce.core.common.exception.ExceptionStatus;
import com.commerce.core.common.properties.GithubKeyProperties;
import com.commerce.core.member.vo.oauth.*;
import com.commerce.core.request.OAuthApiClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class OAuthServiceImpl implements OAuthService {

    private final GithubKeyProperties githubKeyProperties;
    private final OAuthApiClient githubOAuthClient;

    public OAuthServiceImpl(GithubKeyProperties githubKeyProperties, OAuthApiClient githubOAuthClient) {
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
    public OAuthTokenResponse getAccessToken(String type, String code) {
        OAuthType oAuthType = OAuthType.valueOf(type);
        switch (oAuthType) {
            case GITHUB -> {
                GithubAccessTokenResponse githubAccessTokenResponse = this.githubGetAccessToken(code);
                return githubAccessTokenResponse.toResponse();
            }
            default -> throw new CommerceException(ExceptionStatus.VALID_ERROR);
        }
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
