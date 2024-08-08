package com.example.bookbook.controller.bot;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

import com.example.bookbook.domain.dto.bot.AnswerDTO;
import com.example.bookbook.domain.dto.bot.QuestionDTO;
import com.example.bookbook.service.bot.impl.ChatbotService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class WebSocketController {

    private final ChatbotService chatbotService;

    @MessageMapping("/bot/question")
    @SendTo("/topic/bot")
    public AnswerDTO handleWebSocketQuestion(QuestionDTO questionDTO) {
        // 사용자의 질문을 Komoran을 통해 분석하고 결과를 반환
        return chatbotService.processUserQuestion(questionDTO);
    }
}