package com.example.bookbook.domain.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class AnswerDTO {
	
	private long no;
	private String content;
	private String keyword;//name
	private String name;//사용자이름
	
	private List<PhoneInfo> phone;
	
	public AnswerDTO phone(List<PhoneInfo> phone){
		this.phone=phone;
		return this;
	}


}