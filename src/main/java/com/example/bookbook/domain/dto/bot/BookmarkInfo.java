package com.example.bookbook.domain.dto.bot;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class BookmarkInfo {

	private String title;  // 북마크 제목 또는 링크의 제목
    private String url;    // 북마크 URL 또는 링크 주소
}