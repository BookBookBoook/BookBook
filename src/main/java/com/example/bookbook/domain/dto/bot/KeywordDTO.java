package com.example.bookbook.domain.dto.bot;

import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class KeywordDTO {
	private String keyword;
    private int intentionNo;
}
