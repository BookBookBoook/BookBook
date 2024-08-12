package com.example.bookbook.domain.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
public class BookDTO {
    private String title;
    private String author;
    private String publisher;
    private String pubdate;
    private String isbn;
    private String description;
    private String image;
    
    // 추가된 필드들
    private String price;          // 도서 가격
    private String discount;       // 할인 가격
    private String category;       // 도서 카테고리
    private String link;           // 도서 상세 정보 링크
    private Integer page;          // 페이지 수
    private String originalTitle;  // 원제 (번역서의 경우)
}
