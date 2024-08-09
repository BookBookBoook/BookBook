package com.example.bookbook.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

import java.time.LocalDateTime;

@Getter 
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "QuestionAnalysis")
public class QuestionAnalysis {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "qaNo")  // DB 컬럼과 매핑
    private Long qaNo;

    @ManyToOne
    @JoinColumn(name = "questionNo")
    private Question question; // Question 엔티티를 정의해야 합니다

    @Column(name = "keyword")
    private String keyword;

    @Column(name = "createdAt")
    private LocalDateTime createdAt;
}