package com.flyai.safet.service;

import com.flyai.safet.exception.BadRequestException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.http.HttpStatus;
import reactor.core.publisher.Mono;

import java.util.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class GeoCodingService {

    @Value("${services.geo-coding-service}")
    private String GEO_CODING_SERVICE_URL;

    @Value("${cloud.gcp.geo-coding.api-key}")
    private String GEO_CODING_SERVICE_API_KEY;

    public String getAddressFromCoordinate(Double latitude, Double longitude) {

        WebClient webClient = WebClient.builder()
                .baseUrl(GEO_CODING_SERVICE_URL)
                .build();

        Map response = webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/json")
                        .queryParam("latlng", latitude + "," + longitude)
                        .queryParam("key", GEO_CODING_SERVICE_API_KEY)
                        .queryParam("language", "ko")  // 언어 설정을 한국어로
                        .build())
                .retrieve()
                .onStatus(HttpStatus::is4xxClientError, clientResponse -> {
                    log.error("geo-coding-service 4xx error");
                    return Mono.error(new BadRequestException("geo-coding-service 4xx error"));
                })
                .onStatus(HttpStatus::is5xxServerError, clientResponse -> {
                    log.error("geo-coding-service 5xx error");
                    return Mono.error(new BadRequestException("geo-coding-service 5xx error"));
                })
                .bodyToMono(Map.class)
                .block();

        // formatted_address만 추출하는 함수 호출
        return getFormattedAddressFromResponse(response);
    }

    private String getFormattedAddressFromResponse(Map<String, Object> response) {
        if (response != null && response.containsKey("results")) {
            List<Map<String, Object>> results = (List<Map<String, Object>>) response.get("results");
            if (!results.isEmpty()) {
                Map<String, Object> firstResult = results.get(0);
                if (firstResult.containsKey("formatted_address")) {
                    return (String) firstResult.get("formatted_address");
                }
            }
        }
        return null;
    }
}

