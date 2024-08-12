package com.example.bookbook.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

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

@Service
public class BookServiceProcess implements BookService {

	/*
	 * @Value("${kakao.api.key}") private String apiKey;
	 * 
	 * private static final String API_URL =
	 * "https://dapi.kakao.com/v3/search/book";
	 * 
	 * @Override public void searchBooks(String query, Model model) { List<BookDTO>
	 * books = fetchBooksFromApi(query); model.addAttribute("books", books); }
	 * 
	 * private List<BookDTO> fetchBooksFromApi(String query) { RestTemplate
	 * restTemplate = new RestTemplate(); HttpHeaders headers = new HttpHeaders();
	 * headers.set("Authorization", "KakaoAK " + apiKey);
	 * 
	 * HttpEntity<String> entity = new HttpEntity<>(headers);
	 * 
	 * try { ResponseEntity<KakaoApiResponse> response = restTemplate.exchange(
	 * API_URL + "?query=" + query, HttpMethod.GET, entity, KakaoApiResponse.class);
	 * 
	 * return response.getBody().getDocuments(); } catch (RestClientException e) {
	 * log.error("Error while fetching books from API: ", e); return List.of(); // 빈
	 * 리스트 반환 } }
	 */

	// 네이버 API 클라이언트 ID와 시크릿 키를 properties 파일에서 주입받습니다.
	@Value("${naver.openapi.clientId}")
	private String clientId;

	@Value("${naver.openapi.clientSecret}")
	private String clientSecret;

	// 네이버 도서 검색 API의 엔드포인트 URL입니다.
	private static final String NAVER_BOOK_API_URL = "https://openapi.naver.com/v1/search/book.json";

	@Override
	public List<BookDTO> searchBooks(String query) {
		// RestTemplate 객체를 생성합니다. 이를 통해 HTTP 요청을 보냅니다.
		RestTemplate restTemplate = new RestTemplate();

		// API 요청에 필요한 헤더를 설정합니다.
		HttpHeaders headers = new HttpHeaders();
		headers.set("X-Naver-Client-Id", clientId);
		headers.set("X-Naver-Client-Secret", clientSecret);

		// 검색 쿼리를 포함한 전체 URL을 구성합니다.
		String url = NAVER_BOOK_API_URL + "?query=" + query;

		// API에 GET 요청을 보내고 응답을 받습니다.
		ResponseEntity<Map> responseEntity = restTemplate.exchange(url, HttpMethod.GET, new HttpEntity<>(headers),
				Map.class);

		// 응답 본문을 Map 형태로 추출합니다.
		Map<String, Object> responseBody = responseEntity.getBody();

		// 응답에서 도서 정보 리스트를 추출합니다.
		List<Map<String, Object>> items = (List<Map<String, Object>>) responseBody.get("items");

		// BookDTO 객체 리스트를 생성합니다.
		List<BookDTO> books = new ArrayList<>();

		// 각 도서 정보를 BookDTO 객체로 변환하여 리스트에 추가합니다.
		for (Map<String, Object> item : items) {
			BookDTO book = new BookDTO();
			book.setTitle((String) item.get("title"));
			book.setAuthor((String) item.get("author"));
			book.setPublisher((String) item.get("publisher"));
			book.setPubdate((String) item.get("pubdate"));
			book.setIsbn((String) item.get("isbn"));
			book.setDescription((String) item.get("description"));
			book.setImage((String) item.get("image"));
			books.add(book);
		}

		// 변환된 BookDTO 객체 리스트를 반환합니다.
		return books;
	}

	@Override
	public void getDefaultBooks(Model model) {
		List<BookDTO> books = fetchBooksFromAPI("베스트셀러");
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
            book.setIsbn((String) item.get("isbn"));
            book.setDescription((String) item.get("description"));
            book.setImage((String) item.get("image"));
            if (item.containsKey("price")) {
                book.setPubdate((String) item.get("price"));
            }
            books.add(book);
        }
        
        return books;
    }
}