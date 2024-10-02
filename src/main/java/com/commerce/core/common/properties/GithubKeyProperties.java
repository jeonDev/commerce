package com.commerce.core.common.properties;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Getter
@PropertySource("classpath:/properties/github-oauth.yml")
@Component
public class GithubKeyProperties {
    @Value("${client_id}")
    private String clientId;
    @Value("${client_secret}")
    private String clientSecret;
    @Value("${login_url}")
    private String loginUrl;
    @Value(("${oauth-access-token-url}"))
    private String oauthAccessTokenUrl;
}
