package com.project.bookbook.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.bookbook.domain.entity.BookEntity;

public interface CartItemRepository extends JpaRepository<BookEntity, Long>{

}
