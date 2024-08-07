package com.example.bookbook.controller.bot;
/*
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import com.example.bookbook.domain.dto.MessageDTO;
import com.example.bookbook.domain.dto.Question;
import com.example.bookbook.service.bot.KomoranService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Component
public class Receiver {
	
	private final SimpMessagingTemplate smt;
	
	private final KomoranService komoranService;
	private final TemplateEngine templateEngine; // Inject Thymeleaf template engine
	//RabbitTemplate template 에서 전달란 메세지가 전송된다.
	public void receiveMessage(Question dto) {
		System.out.println(">>>>"+dto);
		
		//komoran을 사용해서
		//의도분석->응답메세지 작성
		MessageDTO msg=komoranService.nlpAnalyze(dto.getContent());
	        
		Context thymeleafContext = new Context();
		thymeleafContext.setVariable("msg", msg);
		// Process the Thymeleaf template
		String htmlResponse = templateEngine.process("chatbot/bot-message", thymeleafContext);
		//응답메세지 보내기
		smt.convertAndSend("/topic/question/"+dto.getKey(), htmlResponse);
	}
}
*/