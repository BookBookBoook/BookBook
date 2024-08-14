package com.example.bookbook.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.bookbook.domain.entity.FavoriteBook;

public interface FavoriteBookRepository extends JpaRepository<FavoriteBook, String>{

}
