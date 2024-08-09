package com.example.bookbook.domain.dto.bot;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class QuestionDTO {
    private Long key;
    private Long userId;  // 매퍼에서 변환
    private String content;
    
}
