/*
package com.example.bookbook.controller.bot;

import java.text.MessageFormat;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import com.example.bookbook.domain.dto.bot.AnswerDTO;
import com.example.bookbook.domain.dto.bot.QuestionDTO;
import com.example.bookbook.service.bot.impl.ChatbotService;

import lombok.RequiredArgsConstructor;

@Controller

@RequiredArgsConstructor
public class WebSocketController {

    private final ChatbotService chatbotService;
    
    private final SimpMessagingTemplate messagingTemplate;

    @MessageMapping("/bot/question")
    //@SendTo("/topic/bot")
    public AnswerDTO handleWebSocketQuestion(QuestionDTO questionDTO) {
    	// QuestionDTO에서 userId 필드를 가져옴
       // Long userId = questionDTO.getUserId();

        
        // userId를 사용하여 데이터베이스나 다른 서비스에서 userName을 조회
        // 예를 들어, userService를 통해 조회할 수 있음 (이 부분은 서비스 로직에 따라 구현)
        //String userName = UserService.getUserNameById(userId);
        //userServiceProcess 라면 위에 서비스명 수정
        
    	
        // 사용자의 질문을 Komoran을 통해 분석하고 결과를 반환
        AnswerDTO answer = chatbotService.processUserQuestion(questionDTO);
        
        // 분석 결과를 콘솔에 출력
        //System.out.println("Received question from user ID " + userId + ": " + questionDTO.getQuestionNo());
        
		long key = questionDTO.getKey(); //QuestionDTO에서 key 값을 추출
		String pattern = "{0}님 "+answer.getContent();  //AnswerDTO answer에서 답변추출
		String message=MessageFormat.format(pattern, answer.getName());//MessageFormat을 사용하여 사용자 이름을 포함한 답변 메시지를 포맷
		System.out.println("Analysis result: " + message);
		
		messagingTemplate.convertAndSend("/topic/bot/"+key,message); // "/topic/bot/"+key 경로로 반환

        return answer;
    }
}
*/