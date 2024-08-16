
package com.project.bookbook.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.project.bookbook.domain.dto.bot.AnswerDTO;
import com.project.bookbook.domain.dto.bot.IntentionDTO;
import com.project.bookbook.domain.dto.bot.KeywordDTO;
import com.project.bookbook.domain.dto.bot.MessageDTO;
import com.project.bookbook.domain.dto.bot.QuestionDTO;
import com.project.bookbook.domain.entity.IntentionEntity;
import com.project.bookbook.domain.entity.KeywordEntity;
import com.project.bookbook.domain.entity.UserEntity;
import com.project.bookbook.domain.repository.AnswerRepository;
import com.project.bookbook.domain.repository.KeywordRepository;
import com.project.bookbook.domain.repository.UserEntityRepository;

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
        // 사용자의 질문에 명사가 없어 배열이 비어있을때
        if (analysisResult.getKeywords().isEmpty()){ 
        	intention = IntentionEntity.builder()
        			.answer("죄송합니다만, 제가 답변할 수 없는 질문입니다.")
        			.build();
        }
        
        for (String questionKeyword : analysisResult.getKeywords()) {
            // 키워드를 통해 KeywordEntity를 찾습니다.
            KeywordEntity keywordEntity = keywordRepository.findByKeyword(questionKeyword)
                    .orElse(null);
            
            if (keywordEntity != null) {
                // KeywordEntity에서 intention 추출

                intention = keywordEntity.getIntention();
                
            } 
            else {
            	// 키워드에 해당하는 KeywordEntity가 없을 경우 기본 IntentionEntity를 설정합니다.
                intention = IntentionEntity.builder()
                        .answer("죄송합니다만, 제가 답변할 수 없는 질문입니다.")
                        .build();
            }
            
        /*
        // 명사와 우선순위 매핑
        List<String> topKeywords = getTopKeywords(analysisResult.getKeywords());
        
        for (String keyword : topKeywords) {
            KeywordEntity keywordEntity = keywordRepository.findByKeyword(keyword).orElse(null);

            if (keywordEntity != null) {
                // KeywordEntity에서 intention 추출
                intention = keywordEntity.getIntention();
                if (intention != null) {
                    break; // 첫 번째 유효한 intention이 발견되면 종료
                }
            }
        }

        // 기본 IntentionEntity 설정
        if (intention == null) {
            intention = IntentionEntity.builder()
                    .answer("죄송합니다만, 제가 답변할 수 없는 질문입니다.")
                    .build();
        }
        */
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
    
	/*
	private List<String> getTopKeywords(List<String> keywords) {
	    return keywords.stream()
	            // 각 명사에 대해 해당 KeywordEntity를 찾아 우선순위와 함께 매핑합니다.
	            .map(keyword -> {
	                Optional<KeywordEntity> keywordEntity = keywordRepository.findByKeyword(keyword);
	                // KeywordEntity가 존재하면 우선순위와 함께 매핑하고, 없으면 최대 우선순위를 사용합니다.
	                return keywordEntity.map(entity -> Map.entry(keyword, entity.getPriority()))
	                        .orElse(Map.entry(keyword, Integer.MAX_VALUE));
	            })
	            // 우선순위로 정렬 (오름차순)
	            .sorted(Map.Entry.comparingByValue())
	            // 상위 2개만 가져오기
	            .limit(2)
	            // 키워드만 추출
	            .map(Map.Entry::getKey)
	            .collect(Collectors.toList());
	}
	*/

}