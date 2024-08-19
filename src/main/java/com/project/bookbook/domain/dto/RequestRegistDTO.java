package com.project.bookbook.domain.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.project.bookbook.domain.entity.BookEntity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RequestRegistDTO {

	private List<String> bucketkey;//이미지 버킷키
	private List<String> bookImg;//이미지 이름
	private String bookName; //도서명
	private int discount; //판매가
	private LocalDateTime pubdate; //출간일자
	private String link; //상세정보 url
	private String description; //책 소개
	private String author; //저자
	private String publisher; //출판사
	
	public BookEntity toRequest() {
		return BookEntity.builder()
				.build();
	}
}
