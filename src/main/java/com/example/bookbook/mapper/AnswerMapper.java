package com.example.bookbook.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import com.example.bookbook.domain.dto.bot.AnswerDTO;
import com.example.bookbook.domain.entity.Answer;

@Mapper
public interface AnswerMapper {
    AnswerMapper INSTANCE = Mappers.getMapper(AnswerMapper.class);

    
    @Mapping(source = "question.questionNo", target = "questionNo")  // Question 엔티티의 questionNo를 DTO의 questionNo으로 매핑
    AnswerDTO toDTO(Answer answer);

    @Mapping(source = "questionNo", target = "question.questionNo")  // DTO의 questionNo를 엔티티의 questionNo으로 매핑
    Answer toEntity(AnswerDTO answerDTO);
}
