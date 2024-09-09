package com.project.bookbook.domain.dto;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class MypageReviewDTO {
	
	private long bookNum;
	private String reviewContent;
	private LocalDateTime reviewDate;
	private int rate;
	private int actualOrder;
	
	private String author;
	private String bookImg;
	private String bookName;
	private String isbn;
	
}
