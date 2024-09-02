package com.project.bookbook.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.project.bookbook.config.ReCaptchaConfig;

@Service
public class ReCaptchaService {

    private final ReCaptchaConfig reCaptchaConfig;
    private final WebClient webClient;

    public ReCaptchaService(ReCaptchaConfig reCaptchaConfig, WebClient.Builder webClientBuilder) {
        this.reCaptchaConfig = reCaptchaConfig;
        this.webClient = webClientBuilder.baseUrl(reCaptchaConfig.getVerifyUrl()).build();
    }

    public boolean verifyReCaptcha(String response) {
        Map<String, String> body = new HashMap<>();
        body.put("secret", reCaptchaConfig.getSecretKey());
        body.put("response", response);

        Map<String, Object> verifyResponse = webClient.post()
                .bodyValue(body)
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<Map<String, Object>>() {})
                .block();

        if (verifyResponse == null || verifyResponse.get("success") == null) {
            return false;
        }

        return (Boolean) verifyResponse.get("success");
    }
}