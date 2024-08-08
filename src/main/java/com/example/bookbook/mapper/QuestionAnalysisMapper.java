package com.example.bookbook.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import com.example.bookbook.domain.dto.bot.QuestionAnalysisDTO;
import com.example.bookbook.domain.entity.QuestionAnalysis;

@Mapper
public interface QuestionAnalysisMapper {
    QuestionAnalysisMapper INSTANCE = Mappers.getMapper(QuestionAnalysisMapper.class);

    @Mapping(source = "question.questionNo", target = "questionNo")  // Question 엔티티의 questionNo를 DTO의 questionNo으로 매핑
    QuestionAnalysisDTO toDTO(QuestionAnalysis questionAnalysis);

    @Mapping(source = "questionNo", target = "question.questionNo")  // DTO의 questionNo를 엔티티의 questionNo으로 매핑
    QuestionAnalysis toEntity(QuestionAnalysisDTO questionAnalysisDTO);
    
}
