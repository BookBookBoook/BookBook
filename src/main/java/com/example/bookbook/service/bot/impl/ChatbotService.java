
package com.example.bookbook.service.bot.impl;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.bookbook.domain.dto.bot.AnswerDTO;
import com.example.bookbook.domain.dto.bot.IntentionDTO;
import com.example.bookbook.domain.dto.bot.KeywordDTO;
import com.example.bookbook.domain.dto.bot.MessageDTO;
import com.example.bookbook.domain.dto.bot.QuestionDTO;
import com.example.bookbook.domain.entity.IntentionEntity;
import com.example.bookbook.domain.entity.KeywordEntity;
import com.example.bookbook.domain.entity.UserEntity;
import com.example.bookbook.domain.repository.AnswerRepository;
import com.example.bookbook.domain.repository.KeywordRepository;
import com.example.bookbook.domain.repository.UserEntityRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class ChatbotService {

    //private final QuestionAnalysisRepository questionAnalysisRepository;
	private final ModelMapper modelMapper;
    private final KomoranService komoranService;
    private final KeywordRepository keywordRepository;
    private final AnswerRepository answerRepository;

    @Transactional
    public AnswerDTO processUserQuestion(QuestionDTO questionDTO) {
    	
        MessageDTO analysisResult = komoranService.nlpAnalyze(questionDTO.getContent()); // 코모란 서비스로 메세지 분석
        System.out.println("?>>>>:"+analysisResult);
        
        IntentionEntity intention = null;
        
        for (String questionKeyword : analysisResult.getKeywords()) {
            // 키워드를 통해 KeywordEntity를 찾습니다.
            KeywordEntity keywordEntity = keywordRepository.findByKeyword(questionKeyword)
                    .orElse(null);
            
            if (keywordEntity != null) {
                // KeywordEntity에서 intention 추출

                intention = keywordEntity.getIntention();
                if (intention == null) {
                    // IntentionEntity가 없을 경우 기본값 설정
                    intention = IntentionEntity.builder()
                            .answer("죄송합니다만, 미천한 제가 이해하지못했습니다요.")
                            .build();
                }
            } else {
            	///////////////////////////////////////////////////////////////////
                // 키워드에 해당하는 KeywordEntity가 없을 경우 기본 IntentionEntity를 설정합니다.
                intention = IntentionEntity.builder()
                        .answer("죄송합니다만, 제가 답변할 수 없는 질문입니다.")
                        .build();
            }
         // 만약 첫 번째 키워드로 매칭된 결과가 있으면 바로 반환 
            if (intention != null) {
                break;
            }
        }
        //최종적으로 추출된 IntentionEntity를 DTO로 변환
        IntentionDTO intentionDTO = modelMapper.map(intention, IntentionDTO.class);
        System.out.println("Final Intention: " + intentionDTO);
        
        //AnswerDTO로 변환해서 반환
        AnswerDTO answerDTO = new AnswerDTO();
        answerDTO.setAnswer(intentionDTO.getAnswer());
        
        return answerDTO;
    }
    
    private AnswerDTO generateAnswer(MessageDTO analysisResult) {
    	
    	// analysisResult.getKeywords()를 사용하여 키워드 집합을 가져옴
        // 키워드가 ["자바", "챗봇"]이면, response는 "질문 분석 결과: 자바, 챗봇"
        String response = "질문: " + analysisResult.getContent() + "\n" +
                          "분석된 키워드: " + String.join(", ", analysisResult.getKeywords());
        return AnswerDTO.builder()
                .answer(response)
                .build();
    }
    
}
