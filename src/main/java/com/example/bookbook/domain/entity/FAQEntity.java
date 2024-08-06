package com.example.bookbook.domain.entity;

import java.time.LocalDateTime;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter // Setter는 주로 안 씀
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "FAQ") // 별도로 지정하지 않으면 클래스이름이 테이블명
public class FAQEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long FAQNo;

    @Column(nullable = false, length = 255)
    private String question;

    @Column(nullable = false, length = 255)
    private String answer;

    @Column(nullable = false)
    private LocalDateTime createdAt;
    /*
    // 셀프 조인: 질문이 다른 질문의 답변일 때
    @ManyToOne
    @JoinColumn(name = "parent_question_id")
    private QuestionAnswer parentQuestion;

    @OneToMany(mappedBy = "parentQuestion")
    private List<QuestionAnswer> relatedQuestions;
    */
}

