package com.project.bookbook.service.impl;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Service;

import com.project.bookbook.domain.dto.bot.QuestionDTO;
import com.project.bookbook.service.OpenaiService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OpenaiServiceProcess implements OpenaiService{
	
	private final ChatClient chatClient;

	@Override
	public String aiAnswerProcess(QuestionDTO questionDTO) {
		return chatClient.prompt()
        		.system("책 제목이 뭔지 기억안나. (제목 + 40자 이내 간단한 줄거리)로 간결하게 알려줘")
        		.user(questionDTO.getContent()).call().content();
	}

}