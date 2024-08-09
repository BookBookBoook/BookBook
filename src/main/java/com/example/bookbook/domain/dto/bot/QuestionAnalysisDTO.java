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
public class QuestionAnalysisDTO {
    private Long qaNo;
    private Long questionNo;
    private String keyword;
    private LocalDateTime createdAt;
}
