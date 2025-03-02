package com.commerce.core.member.external;

public interface OAuthApiClient {

    <T, O> O getAccessTokenApiCall(String uri, T request, Class<O> responseClass);
    <O> O getUserInfoApiCall(String uri, String authorization, Class<O> responseClass);
}
