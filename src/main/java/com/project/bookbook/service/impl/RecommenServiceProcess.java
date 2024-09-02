package com.project.bookbook.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.bookbook.domain.dto.api.UserRecommendDTO;
import com.project.bookbook.mapper.OrdersMapper;
import com.project.bookbook.security.CustomUserDetails;
import com.project.bookbook.service.BookService;
import com.project.bookbook.service.RecommendService;
import com.project.bookbook.utils.OpenApiCommonUtil;

import lombok.RequiredArgsConstructor;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

@Service
@RequiredArgsConstructor
public class RecommenServiceProcess implements RecommendService{
	
	private final OrdersMapper ordersMapper;
	private final BookService bookService;
	private final OpenApiCommonUtil openApiUtil;

    @Value("${library.api.authKey}")
    private String authKey;

	@Override
	public void recommendBook(long merchantUid, Model model) {
		List<String> orderBookAuthors = ordersMapper.findByorderBookAuthor(merchantUid);
		System.out.println(orderBookAuthors.toString());
		
		Random random = new Random();
		int randomInt = random.nextInt(orderBookAuthors.size());
		String randomAuthor = orderBookAuthors.get(randomInt);
		
		bookService.searchBooks(randomAuthor, model);
		
	}

	@Override
	public void userRecommend(CustomUserDetails user, Model model) {
		String birthDate = user.getBirthDate();
		int ageGroup = calculateAgeGroup(birthDate);
		
		String userGender = user.getGender();
		int gender = 0; //0:남성
		if(userGender.equals("woman")) {
			gender = 1;
		}
		
		String apiUrl = "http://data4library.kr/api/loanItemSrch";
        
        // URL 파라미터 구성
        StringBuilder urlBuilder = new StringBuilder(apiUrl);
        urlBuilder.append("?authKey=").append(authKey);
        urlBuilder.append("&gender=").append(gender);
        urlBuilder.append("&age=").append(ageGroup);
        urlBuilder.append("&pageSize=4"); //한 페이지당 제공되는 도서 목록 개수
        urlBuilder.append("&format=json");

        // API 호출
        String response = openApiUtil.request(urlBuilder.toString(), null, "GET", null);

        // JSON 응답을 DTO로 변환
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            UserRecommendDTO userRecommendBooks = objectMapper.readValue(response, UserRecommendDTO.class);
            
            model.addAttribute("userRecommendBooks", userRecommendBooks);
            
        } catch (Exception e) {
            // 예외 처리: 로깅 또는 사용자 정의 예외 던지기
            throw new RuntimeException("API 응답을 파싱하는 데 실패했습니다.", e);
        }
		
	}
	
	//연령대 계산
	public static int calculateAgeGroup(String birthDateStr) {
        LocalDate birthDate = LocalDate.parse(birthDateStr);
        LocalDate currentDate = LocalDate.now();
        
        int age = Period.between(birthDate, currentDate).getYears();
        
        if (age >= 0 && age <= 5) {
            return 0;  // 영유아(0~5세)
        } else if (age <= 7) {
            return 6;  // 유아(6~7세)
        } else if (age <= 13) {
            return 8;  // 초등(8~13세)
        } else if (age <= 19) {
            return 14; // 청소년(14~19세)
        } else if (age <= 29) {
            return 20; // 20대
        } else if (age <= 39) {
            return 30; // 30대
        } else if (age <= 49) {
            return 40; // 40대
        } else if (age <= 59) {
            return 50; // 50대
        } else if (age >= 60) {
            return 60; // 60세 이상
        } else {
            return -1; // 미상 (이 경우는 발생하지 않겠지만, 안전을 위해 포함)
        }
    }
	

}