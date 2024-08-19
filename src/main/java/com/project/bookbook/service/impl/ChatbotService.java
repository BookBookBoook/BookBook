package com.project.bookbook.service.impl;

import java.util.Optional;
import java.util.Set;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.project.bookbook.domain.dto.bot.AnswerDTO;
import com.project.bookbook.domain.dto.bot.MessageDTO;
import com.project.bookbook.domain.dto.bot.QuestionDTO;
import com.project.bookbook.domain.entity.AnswerEntity;
import com.project.bookbook.domain.entity.EXKeywordEntity;
import com.project.bookbook.domain.entity.NNPIntentionEntity;
import com.project.bookbook.domain.entity.VVIntentionEntity;
import com.project.bookbook.domain.repository.AnswerRepository;
import com.project.bookbook.domain.repository.EXKeywordRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class ChatbotService {

    private final KomoranService komoranService;
    private final EXKeywordRepository keywordRepository;
    private final AnswerRepository answerRepository;

    @Transactional
    public AnswerDTO processUserQuestion(QuestionDTO questionDTO) {

        // 코모란 서비스로 메시지 분석
        MessageDTO analysisResult = komoranService.nlpAnalyze(questionDTO.getContent());
        System.out.println("분석 결과: " + analysisResult);
        
        // 명사와 동사 집합 추출
        Set<String> nouns = analysisResult.getNouns();
        Set<String> verbs = analysisResult.getVerbs();

        // 명사와 동사로부터 nnp_no와 vv_no 찾기
        NNPIntentionEntity nnpIntention = findNNPIntention(nouns);
        VVIntentionEntity vvIntention = findVVIntention(verbs);

        // nnpNo와 vvNo를 null 체크 후 설정
        int nnpNo;
        int vvNo;
        
        if (nnpIntention != null) {
            nnpNo = nnpIntention.getNnpNo();  // vvIntention이 null이 아니면 getVvNo() 호출
        } else {
            nnpNo = 0;  // vvIntention이 null이면 vvNo를 0으로 설정
            System.out.println("명사: "+nnpNo);
        }
        
        if (vvIntention != null) {
            vvNo = vvIntention.getVvNo();  // vvIntention이 null이 아니면 getVvNo() 호출
        } else {
            vvNo = 0;  // vvIntention이 null이면 vvNo를 0으로 설정
            System.out.println("동사: "+vvNo);
        }
        
        // 매칭된 의도에 따라 답변 찾기
        Optional<AnswerEntity> answerEntityOptional = answerRepository.findByVvIntention_VvNoAndNnpIntention_NnpNo(vvNo, nnpNo);
        
        // 최종적으로 찾은 답변을 DTO로 변환하여 반환
        if (answerEntityOptional.isPresent()) {
            AnswerEntity answerEntity = answerEntityOptional.get();
            return AnswerDTO.builder()
                    .answer(answerEntity.getAnswer())
                    .vvNo(vvNo)
                    .nnpNo(nnpNo)
                    .build();
        } else {
            return AnswerDTO.builder()
                    .answer("죄송부엉.. 고객님의 말씀을 잘 이해하지못한 것이에요..")
                    .vvNo(vvNo)
                    .nnpNo(nnpNo)
                    .build();
        }
    }

    private NNPIntentionEntity findNNPIntention(Set<String> nouns) {// 명사(nouns) 집합에서 적절한 NNPIntentionEntity를 찾는 메서드
        for (String noun : nouns) {// 명사에 해당하는 키워드를 검색
            Optional<EXKeywordEntity> keyword = keywordRepository.findByKeyword(noun);
            
            // 키워드가 존재하고, 해당 키워드에 NNP 의도가 존재하는지 확인
            if (keyword.isPresent() && keyword.get().getNnpIntention() != null) {// NNP 의도가 존재하면 해당 NNPIntentionEntity를 반환
                return keyword.get().getNnpIntention();
            }
        }
        return null;// 적절한 NNPIntentionEntity를 찾지 못한 경우 null을 반환
    }

    
    private VVIntentionEntity findVVIntention(Set<String> verbs) {// 동사(verbs) 집합에서 적절한 VVIntentionEntity를 찾는 메서드
        for (String verb : verbs) {// 동사에 해당하는 키워드를 검색
            Optional<EXKeywordEntity> keyword = keywordRepository.findByKeyword(verb);
            
            // 키워드가 존재하고, 해당 키워드에 VV 의도가 존재하는지 확인
            if (keyword.isPresent() && keyword.get().getVvIntention() != null) {// VV 의도가 존재하면 해당 VVIntentionEntity를 반환
                return keyword.get().getVvIntention();
            }
        }
        return null;// 적절한 VVIntentionEntity를 찾지 못한 경우 null을 반환
    }

}
