/*
 * package com.example.bookbook.service.impl;
 * 
 * import java.util.HashMap; import java.util.Map;
 * 
 * import org.springframework.beans.factory.annotation.Value; import
 * org.springframework.stereotype.Service;
 * 
 * import com.example.bookbook.domain.api.OpenApiUtil; import
 * com.example.bookbook.service.DriveService;
 * 
 * 
 * import lombok.RequiredArgsConstructor;
 * 
 * @Service
 * 
 * @RequiredArgsConstructor public class DriveServiceProcess implements
 * DriveService {
 * 
 * private final OpenApiUtil openApiUtil;
 * 
 * @Value("${naver.client.id}") String clientId;
 * 
 * @Value("${naver.client.secret}") String clientSecret;
 * 
 * @Value("${naver.client.domain}") String domainId;
 * 
 * String domain = "bookbook";
 * 
 * 
 * 
 * //토큰 얻어오는 메서드 private String getAccessToken(String code) {
 * 
 * String apiUrl = "https://auth.worksmobile.com/oauth2/v2.0/token";
 * StringBuilder urlBuilder= new StringBuilder(apiUrl);
 * urlBuilder.append("?code="); urlBuilder.append(code);
 * urlBuilder.append("&grant_type=authorization_code");
 * urlBuilder.append("&client_id="); urlBuilder.append(clientId);
 * urlBuilder.append("&client_secret="); urlBuilder.append(clientSecret);
 * urlBuilder.append("&domain="); urlBuilder.append(domain); apiUrl =
 * urlBuilder.toString();
 * 
 * String method = "POST"; Map<String, String> headers = new HashMap<>();
 * headers.put("Content-Type", "application/x-www-form-urlencoded");
 * 
 * return openApiUtil.request(apiUrl, headers, method, null);
 * 
 * }
 * 
 * }
 */

