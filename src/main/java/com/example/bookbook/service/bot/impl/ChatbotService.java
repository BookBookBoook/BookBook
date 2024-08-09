package com.example.bookbook.service.bot.impl;

import com.example.bookbook.domain.dto.bot.AnswerDTO;
import com.example.bookbook.domain.dto.bot.MessageDTO;
import com.example.bookbook.domain.dto.bot.QuestionAnalysisDTO;
import com.example.bookbook.domain.dto.bot.QuestionDTO;
import com.example.bookbook.domain.entity.Question;
import com.example.bookbook.domain.repository.AnswerRepository;
import com.example.bookbook.domain.repository.QuestionAnalysisRepository;
import com.example.bookbook.domain.repository.QuestionRepository;
import com.example.bookbook.mapper.AnswerMapper;
import com.example.bookbook.mapper.QuestionAnalysisMapper;
import com.example.bookbook.mapper.QuestionMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class ChatbotService {

    private final QuestionRepository questionRepository;
    private final AnswerRepository answerRepository;
    private final QuestionAnalysisRepository questionAnalysisRepository;
    private final KomoranService komoranService;

    @Transactional
    public AnswerDTO processUserQuestion(QuestionDTO questionDTO) {
    	
    	// #1 QuestionDTO를 Question 엔티티로 변환한 후, 데이터베이스에 저장
    	// #2 komoranService를 통해 사용자의 질문 내용을 분석
    	// #3 분석 결과에서 추출된 키워드들을 QuestionAnalysisDTO로 변환하여 저장
    	// #4 분석 결과를 기반으로 generateAnswer 메소드를 호출하여 답변을 생성합니다. 생성된 답변은 AnswerDTO 형태로 데이터베이스에 저장

    	// #1
        Question question = QuestionMapper.INSTANCE.toEntity(questionDTO);
        questionRepository.save(question);

        // #2
        MessageDTO analysisResult = komoranService.nlpAnalyze(questionDTO.getContent());
        
        // #3
        analysisResult.getKeywords().forEach(keyword -> {
            QuestionAnalysisDTO questionAnalysisDTO = QuestionAnalysisDTO.builder()
                    .questionNo(question.getQuestionNo())
                    .keyword(keyword)
                    .build();
            questionAnalysisRepository.save(QuestionAnalysisMapper.INSTANCE.toEntity(questionAnalysisDTO));
        });

        // #4
        AnswerDTO answerDTO = generateAnswer(analysisResult);
        answerRepository.save(AnswerMapper.INSTANCE.toEntity(answerDTO));

        return answerDTO;
    }

    private AnswerDTO generateAnswer(MessageDTO analysisResult) {
    	
    	// 분석 결과로부터 생성된 키워드를 기반으로 답변 내용을 만듭니다. 이 예시에서는 키워드를 콤마로 구분하여 문자열을 생성
        String response = "질문 분석 결과: " + String.join(", ", analysisResult.getKeywords());
        return AnswerDTO.builder()
                .content(response)
                .build();
    }
}
