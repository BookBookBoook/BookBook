package com.example.bookbook.domain.dto.bot;

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
@Setter
@Getter
@Data
public class MessageDTO {
    private String content; // 분석된 내용을 담는 필드
    private Set<String> keywords; // 분석된 키워드들을 담는 필드 (선택 사항)
}