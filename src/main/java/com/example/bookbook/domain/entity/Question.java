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

import org.hibernate.annotations.CreationTimestamp;

@Getter 
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "question")
public class Question {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "questionNo") // DB 컬럼과 매핑
    private Long questionNo;

    @ManyToOne
    @JoinColumn(name = "userId")
    private UserEntity user; // User 엔티티를 정의해야 합니다
    
    public Question user(UserEntity user) {
    	this.user=user;
    	return Question.this;
    }

    @Column(name = "content", nullable = false)
    private String content;

    @Column(columnDefinition = "timestamp")
    @CreationTimestamp
    private LocalDateTime createdAt;
}