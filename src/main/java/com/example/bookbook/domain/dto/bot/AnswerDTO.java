package com.example.bookbook.domain.dto.bot;

import java.time.LocalDateTime;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AnswerDTO {
    private Long answerNo;
    private Long questionNo;  // Question 엔티티의 ID와 일치하도록 수정
    private String content;
    private LocalDateTime createdAt;  // LocalDateTime 타입 사용
}
