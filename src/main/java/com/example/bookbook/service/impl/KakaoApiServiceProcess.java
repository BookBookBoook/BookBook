/*
 * package com.example.bookbook.service.impl;
 * 
 * import java.util.Arrays; import java.util.Collections; import java.util.List;
 * 
 * import org.slf4j.Logger; import org.slf4j.LoggerFactory; import
 * org.springframework.beans.factory.annotation.Value; import
 * org.springframework.cache.annotation.Cacheable; import
 * org.springframework.http.HttpEntity; import
 * org.springframework.http.HttpHeaders; import
 * org.springframework.http.HttpMethod; import
 * org.springframework.http.ResponseEntity; import
 * org.springframework.stereotype.Service; import org.springframework.ui.Model;
 * import org.springframework.web.client.RestClientException; import
 * org.springframework.web.client.RestTemplate; import
 * org.springframework.web.util.UriComponentsBuilder;
 * 
 * import com.example.bookbook.domain.dto.KakaoBookDto; import
 * com.example.bookbook.domain.dto.KakaoSearchDTO; import
 * com.example.bookbook.exception.KakaoApiException;
 * 
 * import lombok.RequiredArgsConstructor;
 * 
 * @Service
 * 
 * @RequiredArgsConstructor public class KakaoApiServiceProcess { private static
 * final Logger logger = LoggerFactory.getLogger(KakaoApiServiceProcess.class);
 * private static final String KAKAO_BOOK_API_URL =
 * "https://dapi.kakao.com/v3/search/book"; // REST API KEY private final
 * RestTemplate restTemplate;
 * 
 * @Value("${kakao.api.key}") private String kakaoApiKey;
 * 
 * @Cacheable(value = "bookSearch", key = "#query + '_' + #page + '_' + #size")
 * public List<KakaoBookDto> searchBooks(String query, int page, int size) {
 * logger.info("Searching books with query: {}, page: {}, size: {}", query,
 * page, size); HttpHeaders headers = new HttpHeaders();
 * headers.set("Authorization", "KakaoAK " + kakaoApiKey);
 * 
 * UriComponentsBuilder builder =
 * UriComponentsBuilder.fromHttpUrl(KAKAO_BOOK_API_URL).queryParam("query",
 * query) .queryParam("page", page).queryParam("size", size);
 * 
 * HttpEntity<?> entity = new HttpEntity<>(headers);
 * 
 * try { ResponseEntity<KakaoSearchDTO> response =
 * restTemplate.exchange(builder.toUriString(), HttpMethod.GET, entity,
 * KakaoSearchDTO.class);
 * 
 * if (response.getBody() != null) { return response.getBody().getDocuments(); }
 * else { logger.warn("Empty response body from Kakao API for query: {}",
 * query); return Collections.emptyList(); } } catch (RestClientException e) {
 * logger.error("Error occurred while calling Kakao API", e); throw new
 * KakaoApiException("카카오 API 호출 중 오류 발생", e); } } }
 */