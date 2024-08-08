package com.example.bookbook.domain.dto.bot;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class QuestionAnalysisDTO {
    private Long id;
    private Long questionNo;
    private String keyword;
    private String createdAt;
}
