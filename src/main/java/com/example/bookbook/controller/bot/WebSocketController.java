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
    	// QuestionDTO에서 userId 필드를 가져옴
        Long userId = questionDTO.getUserId();

        /*
        // userId를 사용하여 데이터베이스나 다른 서비스에서 userName을 조회
        // 예를 들어, userService를 통해 조회할 수 있음 (이 부분은 서비스 로직에 따라 구현)
        String userName = UserService.getUserNameById(userId);
        //userServiceProcess 라면 위에 서비스명 수정
        */
    	
        // 사용자의 질문을 Komoran을 통해 분석하고 결과를 반환
        AnswerDTO answer = chatbotService.processUserQuestion(questionDTO);
        
        // 분석 결과를 콘솔에 출력
        System.out.println("Received question from user ID " + userId + ": " + questionDTO.getQuestionNo());
        System.out.println("Analysis result: " + answer);

        return answer;
    }
}