package com.example.bookbook.domain.dto;

import java.util.List;

import lombok.Data;

@Data
public class KakaoBookDto {
	private Long id; //PK
	private String title;//제목
    private String contents;//소개
    private String url;//상세URL
    private String isbn;//국제표준 도서번호
    private String datetime;//출판날짜
    private List<String> authors;//저자 리스트
    private String publisher;//도서 출판사
    private List<String> translators;//번역자 리스트
    private Integer price;//정가
    private String thumbnail;//표지 미리보기 URL
    private String status;//판매 상태정보(정상,품절,절판 등)

}
