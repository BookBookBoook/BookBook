package com.project.bookbook.domain.dto.bot;

import java.util.List;
import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class IntentionDTO {
	
    private int intentionNo;
    private String intention;
    private String answer;
    private List<KeywordDTO> keywords;
    // getters and setters
}
