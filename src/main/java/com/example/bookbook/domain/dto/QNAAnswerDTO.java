package com.example.bookbook.domain.dto;

import java.time.LocalDateTime;

import com.example.bookbook.domain.entity.QNAEntity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class QNAAnswerDTO {
	
	private long qnaAnswerNum;
	private QNAEntity qna;
	private String title;
	private String content;
	private LocalDateTime date;
	
}
