package com.project.bookbook.domain.dto.bot;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class MessageDTO {
    private String content; // 분석된 내용을 담는 필드 
    private Set<String> keywords; // 질문에서 추출된 주요 키워드를 담는 Set입니다.
}