package com.project.bookbook.service.impl;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.project.bookbook.domain.dto.BookDTO;
import com.project.bookbook.domain.dto.MypageReviewDTO;
import com.project.bookbook.domain.dto.ReviewDTO;
import com.project.bookbook.domain.entity.BookEntity;
import com.project.bookbook.domain.repository.BookRepository;
import com.project.bookbook.mapper.MypageReviewMapper;
import com.project.bookbook.security.CustomUserDetails;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MypageReviewProcess {
	
	private final MypageReviewMapper mypageMapper;
	private final ChatClient chatClient;
	
	public String getReviewsSummaryAI(long randomBookNum) {
		
		List<String> reviewContents = mypageMapper.findAllReview(randomBookNum);
		
		if(reviewContents.isEmpty()) {return null;}
		
		return chatClient.prompt()
        		.system("200자 내외의 글로 요약해줘. 그리고 해요체를 써서 대답해줘.")
        		.user(reviewContents+">>>>> 대괄호 안 내용들은 책에대한 리뷰야. 이 리뷰들의 내용을 긍정적인 리뷰, 부정적인 리뷰로 나눠서 전반적으로 요약, 정리해줘.").call().content();
		
	}

	public void findReviewByUserId(CustomUserDetails user, Model model) {
		long userId = user.getUserId();
		List<MypageReviewDTO> userReviews = mypageMapper.findReviewByUserId(userId);
		model.addAttribute("userReviews", userReviews);
		
		List<String> userReviewTexts = mypageMapper.findReviewContentsByUserId(userId);
		List<String> reviewBookNames = mypageMapper.findReviewBooksByUserId(userId);
		
		String chatGptWords = chatClient.prompt()
			.system("키워드만 오직 단어로 ','으로 구분해서 답해줘. 예를 들어 만화, 경제, 코딩. 그리고 단어는 6개 이하로.")
			.user(reviewBookNames+","+userReviewTexts+">>>>> 첫 번째 대괄호 안 내용은 리뷰를 쓴 책들이고, 두번째 대괄호 안 내용들은 순서대로 책들에 대한 자기가 쓴 리뷰야. 책 이름과 리뷰 내용을 종합해서 이 사람의 도서 이름 검색 추천검색어를 단어로 추출해줘.").call().content();
		System.out.println("리뷰 중에서 추출한 키워드 : "+chatGptWords);
		
		List<String> userKeyWords = Arrays.stream(chatGptWords.split(","))
                .map(String::trim)
                .collect(Collectors.toList());
		System.out.println("추출한 키워드 리스트 : "+userKeyWords);
		
		model.addAttribute("userKeyWords", userKeyWords);
	}

}
