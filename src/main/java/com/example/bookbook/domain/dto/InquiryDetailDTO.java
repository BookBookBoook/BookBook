package com.example.bookbook.domain.dto;

import java.time.LocalDateTime;

import com.example.bookbook.domain.entity.QNAEntity;
import com.example.bookbook.domain.entity.UserEntity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class InquiryDetailDTO {
	
	private long qnaAnswerNum;
	private String title; //답변 제목
	private QNAEntity qna; // 질문 fk
	private String content;
	private LocalDateTime date;
}
