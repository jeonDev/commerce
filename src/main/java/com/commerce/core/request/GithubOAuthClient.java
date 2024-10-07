package com.commerce.core.request;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
public class GithubOAuthClient implements OAuthApiClient {

    private final WebClient webClient;

    public GithubOAuthClient(WebClient webClient) {
        this.webClient = webClient;
    }

    @Override
    public <T, O> O getAccessTokenApiCall(String uri, T request, Class<O> responseClass) {
        ResponseEntity<O> responseEntity = webClient.post()
                .uri(uri)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .bodyValue(request)
                .retrieve()
                .toEntity(responseClass)
                .block();

        assert responseEntity != null;
        return responseEntity.getBody();
    }

    @Override
    public <O> O getUserInfoApiCall(String uri, String authorization, Class<O> responseClass) {
        ResponseEntity<O> responseEntity = webClient.get()
                .uri(uri)
                .accept(MediaType.APPLICATION_JSON)
                .headers(httpHeaders ->
                        httpHeaders.add("Authorization", authorization)
                )
                .retrieve()
                .toEntity(responseClass)
                .block();

        assert responseEntity != null;
        return responseEntity.getBody();
    }
}
