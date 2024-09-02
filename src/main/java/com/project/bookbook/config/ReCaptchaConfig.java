package com.project.bookbook.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ReCaptchaConfig {
	
    @Value("${google.recaptcha.key.site}")
    private String siteKey;

    @Value("${google.recaptcha.key.secret}")
    private String secretKey;

    @Value("${google.recaptcha.v3.url}")
    private String verifyUrl;

    public String getSiteKey() {
        return siteKey;
    }

    public String getSecretKey() {
        return secretKey;
    }

    public String getVerifyUrl() {
        return verifyUrl;
    }
}