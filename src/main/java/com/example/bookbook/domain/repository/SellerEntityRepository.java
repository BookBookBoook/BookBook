package com.example.bookbook.domain.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.bookbook.domain.entity.SellerEntity;

public interface SellerEntityRepository extends JpaRepository<SellerEntity, Long> {
	 Optional<SellerEntity> findByBusinessNum(String businessNum);
}