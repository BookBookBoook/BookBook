package com.example.bookbook.controller.bot;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;

import com.example.bookbook.domain.dto.Question;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller // /message/**  
public class MessageController {
	
	private final RabbitTemplate template;
	
	@Value("${spring.rabbitmq.template.exchange}")
	private String exchange;
	@Value("${spring.rabbitmq.template.routing-key}")
	private String routingKey;
	// /message/bot
	@MessageMapping("/bot")
	public void bot(Question dto) {
		template.convertAndSend(exchange, routingKey, dto);
	}
}