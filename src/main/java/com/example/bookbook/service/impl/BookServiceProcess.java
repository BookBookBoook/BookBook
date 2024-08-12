package com.example.bookbook.service.impl;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.example.bookbook.domain.dto.BookDTO;
import com.example.bookbook.service.BookService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class BookServiceProcess implements BookService {

	@Value("${kakao.api.key}")
    private String apiKey;

    private static final String API_URL = "https://dapi.kakao.com/v3/search/book";

    @Override
    public void searchBooks(String query, Model model) {
        List<BookDTO> books = fetchBooksFromApi(query);
        model.addAttribute("books", books);
    }

    private List<BookDTO> fetchBooksFromApi(String query) {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "KakaoAK " + apiKey);

        HttpEntity<String> entity = new HttpEntity<>(headers);

        try {
            ResponseEntity<KakaoApiResponse> response = restTemplate.exchange(
                    API_URL + "?query=" + query,
                    HttpMethod.GET,
                    entity,
                    KakaoApiResponse.class);

            return response.getBody().getDocuments();
        } catch (RestClientException e) {
            log.error("Error while fetching books from API: ", e);
            return List.of(); // 빈 리스트 반환
        }
    }

}