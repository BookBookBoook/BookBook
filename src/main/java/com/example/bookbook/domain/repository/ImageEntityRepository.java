package com.example.bookbook.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.bookbook.domain.entity.ImageEntity;

public interface ImageEntityRepository extends JpaRepository<ImageEntity, Long> {
}