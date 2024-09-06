package com.project.bookbook.service.impl;

import java.util.List;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Service;

import com.project.bookbook.domain.dto.BookDTO;
import com.project.bookbook.domain.dto.ReviewDTO;
import com.project.bookbook.domain.entity.BookEntity;
import com.project.bookbook.domain.repository.BookRepository;
import com.project.bookbook.mapper.MypageReviewMapper;

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

}
