package com.example.bookbook.domain.repository;

import com.example.bookbook.domain.entity.Answer;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface AnswerRepository extends JpaRepository<Answer, Long> {

	Optional<Answer> findByKeyword(String keyword);
}

