package com.example.bookbook.domain.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.bookbook.domain.entity.ChatBotIntentionEntity;

public interface ChatBotIntentionRepository extends JpaRepository<ChatBotIntentionEntity, Long> {
	
	Optional<ChatBotIntentionEntity> findByNameAndUpper(String name, ChatBotIntentionEntity upper);

}
