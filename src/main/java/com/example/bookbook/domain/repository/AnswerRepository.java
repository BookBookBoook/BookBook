package com.example.bookbook.domain.repository;

import com.example.bookbook.domain.entity.Answer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AnswerRepository extends JpaRepository<Answer, Long> {
}

