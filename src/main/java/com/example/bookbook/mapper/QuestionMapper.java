package com.example.bookbook.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import com.example.bookbook.domain.dto.bot.QuestionDTO;
import com.example.bookbook.domain.entity.Question;

@Mapper
public interface QuestionMapper {
    QuestionMapper INSTANCE = Mappers.getMapper(QuestionMapper.class);
    
    // Question 엔티티를 QuestionDTO로 변환
    //@Mapping(source = "user.userId", target = "userId")  // User 엔티티의 userId를 DTO의 userId로 매핑
    QuestionDTO toDTO(Question question);
    
    //@Mapping(source = "userId", target = "user.userId")  // DTO의 userId를 엔티티의 User.id로 매핑
    Question toEntity(QuestionDTO questionDTO);
/*
    @Mapping(source = "user.userId", target = "userId", defaultValue = "defaultUserId")  // defaultValue 사용
    QuestionDTO toDTO(Question question);

    @Mapping(source = "userId", target = "user.userId", defaultExpression = "java(null)") // null 초기화
    Question toEntity(QuestionDTO questionDTO);
*/    
}


