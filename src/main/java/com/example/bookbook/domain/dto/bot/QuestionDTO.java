package com.example.bookbook.domain.dto.bot;

import java.time.LocalDate;
import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class QuestionDTO {
    private Long questionNo;
    private Long userId;  // 매퍼에서 변환
    private String content;
    private LocalDateTime createdAt;
}
