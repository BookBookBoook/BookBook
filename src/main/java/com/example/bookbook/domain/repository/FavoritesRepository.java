package com.example.bookbook.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.bookbook.domain.entity.BookEntity;

@Repository
public interface FavoritesRepository extends JpaRepository<BookEntity, Long>{

}
