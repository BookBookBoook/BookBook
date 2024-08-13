/*
package com.example.bookbook.service.bot.impl;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.bookbook.domain.dto.bot.AnswerDTO;
import com.example.bookbook.domain.dto.bot.MessageDTO;
import com.example.bookbook.domain.dto.bot.QuestionDTO;
import com.example.bookbook.domain.entity.Answer;
import com.example.bookbook.domain.entity.Question;
import com.example.bookbook.domain.entity.UserEntity;
import com.example.bookbook.domain.repository.AnswerRepository;
import com.example.bookbook.domain.repository.QuestionRepository;
import com.example.bookbook.domain.repository.UserEntityRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class ChatbotService {

    private final QuestionRepository questionRepository;
    private final AnswerRepository answerRepository;
    //private final QuestionAnalysisRepository questionAnalysisRepository;
    private final KomoranService komoranService;
    private final ModelMapper modelMapper;
    private final UserEntityRepository userRepo;

    @Transactional
    public AnswerDTO processUserQuestion(QuestionDTO questionDTO) {
    	
    	// #1 QuestionDTO를 Question 엔티티로 변환한 후, 데이터베이스에 저장
    	// #2 komoranService를 통해 사용자의 질문 내용을 분석
    	// #3 분석 결과에서 추출된 키워드들을 QuestionAnalysisDTO로 변환하여 저장
    	// #4 분석 결과를 기반으로 generateAnswer 메소드를 호출하여 답변을 생성합니다. 생성된 답변은 AnswerDTO 형태로 데이터베이스에 저장

    	// #1
    	Question question=modelMapper.map(questionDTO, Question.class);
    	UserEntity user=userRepo.findById(questionDTO.getUserId()).orElseThrow();
    	question=question.user(user);
    	System.out.println("Question>>>>:"+question);
        //questionRepository.save(question);
       /
        // #2
        MessageDTO analysisResult = komoranService.nlpAnalyze(questionDTO.getContent());
        System.out.println("?>>>>:"+analysisResult);
        Answer answer=null;
        // #3
        
        for(String keyword : analysisResult.getKeywords()) {
        	answer=answerRepository.findByKeyword(keyword)
        			.orElse(Answer.builder().content("죄송합니다만, 제가 답변 할수 없는 질문입니다.").build());
        }

        // #4
       
        //
        AnswerDTO answerDTO=modelMapper.map(answer, AnswerDTO.class);
        return answerDTO.name(user.getUserName());
    }

    private AnswerDTO generateAnswer(MessageDTO analysisResult) {
    	
    	// analysisResult.getKeywords()를 사용하여 키워드 집합을 가져옴
        // 키워드가 ["자바", "챗봇"]이면, response는 "질문 분석 결과: 자바, 챗봇"
        String response = "질문: " + analysisResult.getContent() + "\n" +
                          "분석된 키워드: " + String.join(", ", analysisResult.getKeywords());
        return AnswerDTO.builder()
                .content(response)
                .build();
    }
    
}
*/