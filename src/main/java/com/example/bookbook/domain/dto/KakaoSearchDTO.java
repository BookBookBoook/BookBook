package com.example.bookbook.domain.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class KakaoSearchDTO {
	private List<KakaoBookDto> documents;
	private Meta meta;
	
	@Data
	public static class Meta{
		@JsonProperty("is_end")
        private boolean isEnd;
        @JsonProperty("pageable_count")
        private int pageableCount;
        @JsonProperty("total_count")
        private int totalCount;
	}
}
