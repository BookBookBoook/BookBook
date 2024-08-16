package com.project.bookbook.domain.dto.bot;

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
    private String answer;
    private String intention;
    public AnswerDTO intention(String intention) {
    	this.intention=intention;
    	return this;
    }
}
