package com.example.bookbook.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.bookbook.domain.entity.BookEntity;

public interface CartItemRepository extends JpaRepository<BookEntity, Long>{

}
