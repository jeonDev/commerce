package com.commerce.core.member.service;

import com.commerce.core.common.exception.CommerceException;
import com.commerce.core.common.exception.ExceptionStatus;
import com.commerce.core.common.properties.GithubKeyProperties;
import com.commerce.core.member.vo.OAuthType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class OAuthServiceImpl implements OAuthService {

    private final GithubKeyProperties githubKeyProperties;

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
    public String getAccessToken(String code) {
        return null;
    }

    private String githubGetPage() {
        return githubKeyProperties.getLoginUrl() + "?client_id=" + githubKeyProperties.getClientId();
    }

}
