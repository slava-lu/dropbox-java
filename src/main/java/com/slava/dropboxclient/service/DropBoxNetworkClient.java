package com.slava.dropboxclient.service;

import com.slava.dropboxclient.model.auth.DropBoxAuthResponse;
import com.slava.dropboxclient.model.info.DropBoxInfoResponse;
import com.slava.dropboxclient.model.list.DropBoxListResponse;
import com.slava.dropboxclient.model.list.DropBoxMetaResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.codec.LoggingCodecSupport;
import org.springframework.stereotype.Service;
import org.springframework.util.Base64Utils;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.client.ExchangeStrategies;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.nio.charset.StandardCharsets.UTF_8;

@Service
public class DropBoxNetworkClient {

    private final WebClient webClient;

    public DropBoxNetworkClient() {
        final String rootUrl = "https://api.dropboxapi.com";

        // this is for detailed logging of headers
        ExchangeStrategies exchangeStrategies = ExchangeStrategies.withDefaults();
        exchangeStrategies
                .messageWriters().stream()
                .filter(LoggingCodecSupport.class::isInstance)
                .forEach(writer -> ((LoggingCodecSupport) writer).setEnableLoggingRequestDetails(true));

        this.webClient = WebClient
                .builder()
                .exchangeStrategies(exchangeStrategies)
                .defaultHeader(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE)
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .baseUrl(rootUrl)
                .build();
    }

    public DropBoxAuthResponse getAccessToken(String authCode, String appKey, String appSecret) {
        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("code", authCode);
        body.add("grant_type", "authorization_code");

        return webClient.post()
                .uri("/oauth2/token")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .header("Authorization", "Basic " + Base64Utils
                        .encodeToString((appKey + ":" + appSecret).getBytes(UTF_8)))
                .syncBody(body)
                .retrieve()
                .bodyToMono(DropBoxAuthResponse.class)
                .block();
    }

    public DropBoxInfoResponse getAccountInfo(String accessToken) {
        return webClient.post()
                .uri("/2/users/get_current_account")
                .header("Authorization", "Bearer " + accessToken)
                .syncBody("null")
                .retrieve()
                .bodyToMono(DropBoxInfoResponse.class)
                .block();
    }

    public List<Object> getMetaList(String accessToken, String path) {
        Map<String, String> body = new HashMap<>();
        body.put("path", path);

        Mono<DropBoxMetaResponse> getMeta = webClient.post()
                .uri("2/files/get_metadata")
                .header("Authorization", "Bearer " + accessToken)
                .syncBody(body)
                .retrieve()
                .bodyToMono(DropBoxMetaResponse.class);

        Mono<DropBoxListResponse> getList = webClient.post()
                .uri("/2/files/list_folder")
                .header("Authorization", "Bearer " + accessToken)
                .syncBody(body)
                .retrieve()
                .bodyToMono(DropBoxListResponse.class);

        return Flux.merge(getMeta, getList).collectList().block();
    }
}
