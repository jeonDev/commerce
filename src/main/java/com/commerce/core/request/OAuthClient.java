package com.commerce.core.request;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
public class OAuthClient {

    private final WebClient webClient;

    public OAuthClient(WebClient webClient) {
        this.webClient = webClient;
    }

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
}
