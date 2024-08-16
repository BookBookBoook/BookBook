package com.project.bookbook.domain.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.bookbook.domain.entity.IntentionEntity;

public interface AnswerRepository extends JpaRepository<IntentionEntity, Integer>{
	Optional<IntentionEntity> findByIntention (String intention);

	


}
