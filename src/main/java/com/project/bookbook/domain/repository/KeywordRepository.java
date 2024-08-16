package com.project.bookbook.domain.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.bookbook.domain.entity.KeywordEntity;

public interface KeywordRepository extends JpaRepository<KeywordEntity, String>{

	Optional<KeywordEntity> findByKeyword(String keyword);


}
