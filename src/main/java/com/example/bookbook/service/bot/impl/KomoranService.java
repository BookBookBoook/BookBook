package com.example.bookbook.service.bot.impl;

import org.springframework.stereotype.Service;

import com.example.bookbook.domain.dto.bot.MessageDTO;

import kr.co.shineware.nlp.komoran.core.Komoran;
import kr.co.shineware.nlp.komoran.model.KomoranResult;
import lombok.RequiredArgsConstructor;


import java.util.Set;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class KomoranService {

    private final Komoran komoran; // Komoran 형태소 분석기

    public MessageDTO nlpAnalyze(String message) {
    	
        // Komoran을 사용하여 메시지 분석
        KomoranResult result=komoran.analyze(message);
		result.getTokenList().forEach(token->{
			System.out.format("(%2d, %2d) %s/%s\n", token.getBeginIndex(), token.getEndIndex(),
					token.getMorph(), token.getPos());
		});
		
		//문자에서 명사들만 추출한 목록 중복제거해서 set
		Set<String> nouns=result.getNouns().stream()
				.collect(Collectors.toSet());
		nouns.forEach((noun)->{
			System.out.println(">>>:"+ noun);
		});//메세지에서 명사추출

        // 명사 집합을 기반으로 응답 메시지 생성
        return analyzeToken(nouns);
    }

    private MessageDTO analyzeToken(Set<String> nouns) {
    	
    	// 분석된 명사를 기반으로 응답 메시지를 생성하는 로직
    	String analyzedContent = "다음과 같은 키워드를 분석했습니다: " + String.join(", ", nouns);
    	
        // 예를 들어, 단순히 메시지와 명사를 반환하는 메시지 DTO 생성
        MessageDTO messageDTO = MessageDTO.builder()
                .content(analyzedContent)
                .keywords(nouns)
                .build();

        // 분석된 명사와 관련된 추가 로직을 여기에 추가할 수 있습니다.
        return messageDTO;
    }
}