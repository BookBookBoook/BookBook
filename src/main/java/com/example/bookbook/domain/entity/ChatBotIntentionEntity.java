package com.example.bookbook.domain.entity;

import java.time.LocalDateTime;

import com.example.bookbook.domain.dto.AnswerDTO;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter // Setter는 주로 안 씀
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "ChatBotIntention")
public class ChatBotIntentionEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // 고유 식별자

    @Column(nullable = false, unique = true)
    private String name; // 의도 이름

    @ManyToOne
    @JoinColumn(name = "upper_id") // 상위 의도를 참조하는 외래 키
    private ChatBotIntentionEntity upper; // 상위 의도
/*
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "answer_id") // 응답을 참조하는 외래 키
    private AnswerDTO answer; // 의도에 대한 응답
*/

}