package com.example.bookbook.controller.bot;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.example.bookbook.domain.dto.bot.AnswerDTO;
import com.example.bookbook.domain.dto.bot.QuestionDTO;
import com.example.bookbook.service.bot.impl.ChatbotService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class ChatBotController {
	/*
	private final RabbitTemplate template;
	*/
	private final ChatbotService chatbotService;
	/*
	@Value("${spring.rabbitmq.template.exchange}")
	private String exchange;
	
	@Value("${spring.rabbitmq.template.routing-key}")
	private String routingKey;
	
	// /message/bot
	@MessageMapping("/bot")
	public void bot(Question dto) {
		template.convertAndSend(exchange, routingKey, dto);
	}
	*/
	
    @PostMapping("/ask")
    public AnswerDTO askQuestion(@RequestBody QuestionDTO questionDTO) {
        // 사용자의 질문을 Komoran을 통해 분석하고 결과를 반환
        return chatbotService.processUserQuestion(questionDTO);
    }
	
}