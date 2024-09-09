package com.project.bookbook.domain.dto;

import java.time.LocalDateTime;

import lombok.Getter;

@Getter
public class SearchQueryDTO {
	
	private long searchId;
	private String searchQuery;
	private LocalDateTime searchDate;

}
