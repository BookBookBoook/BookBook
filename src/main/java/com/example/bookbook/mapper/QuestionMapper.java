package com.example.bookbook.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import com.example.bookbook.domain.dto.bot.QuestionDTO;
import com.example.bookbook.domain.entity.Question;

@Mapper
public interface QuestionMapper {
    QuestionMapper INSTANCE = Mappers.getMapper(QuestionMapper.class);

    @Mapping(source = "user.userId", target = "userId")  // User 엔티티의 userId를 DTO의 userId로 매핑
    QuestionDTO toDTO(Question question);
    
    @Mapping(source = "questionNo", target = "question.questionNo")  // DTO의 userId를 엔티티의 userId으로 매핑
    Question toEntity(QuestionDTO questionDTO);

}


