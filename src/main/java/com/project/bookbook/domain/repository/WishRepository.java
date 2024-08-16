package com.project.bookbook.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.bookbook.domain.entity.FavoriteBook;
import com.project.bookbook.domain.entity.UserEntity;
import com.project.bookbook.domain.entity.WishEntity;

public interface WishRepository extends JpaRepository<WishEntity, Long>{

	boolean existsByUserAndFavoriteBook(UserEntity user, FavoriteBook favoriteBook);

}
