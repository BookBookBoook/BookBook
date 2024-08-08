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
        Question question = QuestionMapper.INSTANCE.toEntity(questionDTO);
        questionRepository.save(question);

        MessageDTO analysisResult = komoranService.nlpAnalyze(questionDTO.getContent());
        
        analysisResult.getKeywords().forEach(keyword -> {
            QuestionAnalysisDTO questionAnalysisDTO = QuestionAnalysisDTO.builder()
                    .questionNo(question.getQuestionNo())
                    .keyword(keyword)
                    .build();
            questionAnalysisRepository.save(QuestionAnalysisMapper.INSTANCE.toEntity(questionAnalysisDTO));
        });

        AnswerDTO answerDTO = generateAnswer(analysisResult);
        answerRepository.save(AnswerMapper.INSTANCE.toEntity(answerDTO));

        return answerDTO;
    }

    private AnswerDTO generateAnswer(MessageDTO analysisResult) {
        String response = "질문 분석 결과: " + String.join(", ", analysisResult.getKeywords());
        return AnswerDTO.builder()
                .content(response)
                .build();
    }
}
