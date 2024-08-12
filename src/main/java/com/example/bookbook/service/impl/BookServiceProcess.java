package com.example.bookbook.service.impl;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.example.bookbook.domain.dto.BookDTO;
import com.example.bookbook.domain.dto.NaverBookItem;
import com.example.bookbook.domain.dto.NaverBookResponse;
import com.example.bookbook.service.BookService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
public class BookServiceProcess implements BookService {

	@Value("${naver.openapi.clientId}")
	private String clientId;

	@Value("${naver.openapi.clientSecret}")
	private String clientSecret;

	private static final String NAVER_BOOK_API_URL = "https://openapi.naver.com/v1/search/book.json";

	public void searchBooks(String query, Model model) {
        try {
            String encodedQuery = URLEncoder.encode(query, StandardCharsets.UTF_8);
            String url = NAVER_BOOK_API_URL + "?query=" + encodedQuery + "&display=20"; // 결과 수를 100개로 설정

            HttpHeaders headers = new HttpHeaders();
            headers.set("X-Naver-Client-Id", clientId);
            headers.set("X-Naver-Client-Secret", clientSecret);
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

            RestTemplate restTemplate = new RestTemplate();
            ResponseEntity<NaverBookResponse> responseEntity = restTemplate.exchange(
                url,
                HttpMethod.GET,
                new HttpEntity<>(headers),
                NaverBookResponse.class
            );

            NaverBookResponse response = responseEntity.getBody();
            if (response != null && response.getItems() != null && !response.getItems().isEmpty()) {
                List<BookDTO> books = response.getItems().stream()
                    .map(this::convertToBookDTO)
                    .collect(Collectors.toList());
                model.addAttribute("books", books);
                model.addAttribute("query", query);
                logger.info("검색 결과: {} 건", books.size());
            } else {
                model.addAttribute("books", Collections.emptyList());
                model.addAttribute("error", "검색 결과가 없습니다.");
                logger.info("검색 결과 없음: {}", query);
            }
        } catch (Exception e) {
            logger.error("책 검색 중 오류 발생: {}", e.getMessage(), e);
            model.addAttribute("books", Collections.emptyList());
            model.addAttribute("error", "검색 중 오류가 발생했습니다: " + e.getMessage());
        }
    }

    private BookDTO convertToBookDTO(NaverBookItem item) {
        BookDTO book = new BookDTO();
        book.setTitle(item.getTitle() != null ? item.getTitle() : "");
        book.setAuthor(item.getAuthor() != null ? item.getAuthor() : "");
        book.setPublisher(item.getPublisher() != null ? item.getPublisher() : "");
        book.setPubdate(item.getPubdate() != null ? item.getPubdate() : "");
        book.setDescription(truncateDescription(item.getDescription()));
        book.setIsbn(item.getIsbn() != null ? item.getIsbn() : "");
        book.setImage(item.getImage() != null ? item.getImage() : "");
        book.setLink(item.getLink() != null ? item.getLink() : "");
        book.setDiscount(item.getDiscount() != null ? item.getDiscount() : "");
        return book;
    }
    
	/**/

	@Override
	public void getDefaultBooks(Model model) {
		List<BookDTO> books = fetchBooksFromAPI("만화");
		model.addAttribute("books", books);
	}

	private List<BookDTO> fetchBooksFromAPI(String query) {
		RestTemplate restTemplate = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		headers.set("X-Naver-Client-Id", clientId);
		headers.set("X-Naver-Client-Secret", clientSecret);

		String url = NAVER_BOOK_API_URL + "?query=" + query;

		ResponseEntity<Map> responseEntity = restTemplate.exchange(url, HttpMethod.GET, new HttpEntity<>(headers),
				Map.class);

		Map<String, Object> responseBody = responseEntity.getBody();
		return parseBookResults(responseBody);
	}

	private List<BookDTO> parseBookResults(Map<String, Object> responseBody) {
		List<Map<String, Object>> items = (List<Map<String, Object>>) responseBody.get("items");
		List<BookDTO> books = new ArrayList<>();

		for (Map<String, Object> item : items) {
			BookDTO book = new BookDTO();
			book.setTitle((String) item.get("title"));
			book.setAuthor((String) item.get("author"));
			book.setPublisher((String) item.get("publisher"));
			book.setPubdate((String) item.get("pubdate"));
			String fullDescription = (String) item.get("description");
			book.setDescription(truncateDescription(fullDescription));
			// ISBN 파싱 로직 수정
			book.setIsbn((String) item.get("isbn"));
			book.setImage((String) item.get("image"));
			book.setLink((String) item.get("link"));
			book.setDiscount((String) item.get("discount")); // 판매가

			// 카테고리 정보가 있다면 설정
			if (item.containsKey("category")) {
				book.setCategory((String) item.get("category"));
			}

			books.add(book);
		}

		return books;
	}

	private static final Logger logger = LoggerFactory.getLogger(BookServiceProcess.class);

	private String truncateDescription(String description) {
	    logger.debug("Original description: {}", description);
	    
	    if (description == null) {
	        return "";
	    }
	    
	    // 300자 이하면 그대로 반환
	    if (description.length() <= 250) {
	        return description;
	    }
	    
	    // 300자까지 자르기
	    String truncated = description.substring(0, 250);
	    
	    // 단어 중간에서 잘리는 것을 방지하기 위해 마지막 공백 이후로 자르기
	    int lastSpace = truncated.lastIndexOf(' ');
	    if (lastSpace > 0) {
	        truncated = truncated.substring(0, lastSpace);
	    }
	    
	    // 생략 부호 추가
	    String result = truncated + "...";
	    
	    logger.debug("Truncated description: {}", result);
	    return result;
	}

	@Override
	public BookDTO getBookByIsbn(String isbn) {
		RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.set("X-Naver-Client-Id", clientId);
        headers.set("X-Naver-Client-Secret", clientSecret);
        
        String url = NAVER_BOOK_API_URL + "?query=isbn:" + isbn;

        ResponseEntity<NaverBookResponse> responseEntity = restTemplate.exchange(
            url,
            HttpMethod.GET,
            new HttpEntity<>(headers),
            NaverBookResponse.class
        );

        NaverBookResponse response = responseEntity.getBody();
        if (response != null && !response.getItems().isEmpty()) {
            return convertToBookDTO(response.getItems().get(0));
        }
        return null;
    }

    private BookDTO convertToBookDTO1(NaverBookItem item) {
        BookDTO book = new BookDTO();
        book.setTitle(item.getTitle());
        book.setAuthor(item.getAuthor());
        book.setPublisher(item.getPublisher());
        book.setPubdate(item.getPubdate());
        book.setDescription(item.getDescription());
        book.setIsbn(item.getIsbn());
        book.setImage(item.getImage());
        book.setLink(item.getLink());
        book.setDiscount(item.getDiscount());
        // 기타 필요한 필드 설정
        return book;
    }

	

}