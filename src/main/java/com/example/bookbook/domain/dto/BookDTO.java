package com.example.bookbook.domain.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class BookDTO {
    private String title;
    private List<String> authors;
    private String thumbnail;
    private int price;
    private String contents;

    private String isbn;           // ISBN
    private String publisher;      // 출판사
    private int salePrice;         // 판매가
    private String status;         // 판매 상태
    private String category;       // 카테고리
    private String url;            // 상세 페이지 URL
    private List<String> translators;    // 번역자 (있는 경우)
}
