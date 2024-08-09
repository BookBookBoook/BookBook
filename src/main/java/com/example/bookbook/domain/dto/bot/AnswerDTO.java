package com.example.bookbook.domain.dto.bot;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AnswerDTO {
    private String content;
    private String name;
    public AnswerDTO name(String name) {
    	this.name=name;
    	return this;
    }
}
