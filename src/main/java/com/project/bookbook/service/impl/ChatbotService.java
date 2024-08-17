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

        // 유효한 nnpIntention과 vvIntention이 있는지 확인
        if (nnpIntention == null || vvIntention == null) {
            return AnswerDTO.builder()
                    .answer("죄송합니다만, 적절한 답변을 찾을 수 없습니다.")
                    .vvNo(0)  // 기본값 설정
                    .nnpNo(0)  // 기본값 설정
                    .build();
        }
        
        // nnpIntention과 vvIntention을 nnpNo와 vvNo로 변환
        int nnpNo = nnpIntention.getNnpNo();
        int vvNo = vvIntention.getVvNo();
        
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
                    .answer("죄송합니다만, 제가 답변할 수 없는 질문입니다.")
                    .vvNo(vvNo)
                    .nnpNo(nnpNo)
                    .build();
        }
    }

    private NNPIntentionEntity findNNPIntention(Set<String> nouns) {
        for (String noun : nouns) {
            Optional<EXKeywordEntity> keyword = keywordRepository.findByKeyword(noun);
            if (keyword.isPresent() && keyword.get().getNnpIntention() != null) {
                return keyword.get().getNnpIntention();
            }
        }
        return null;
    }

    private VVIntentionEntity findVVIntention(Set<String> verbs) {
        for (String verb : verbs) {
            Optional<EXKeywordEntity> keyword = keywordRepository.findByKeyword(verb);
            if (keyword.isPresent() && keyword.get().getVvIntention() != null) {
                return keyword.get().getVvIntention();
            }
        }
        return null;
    }
}
