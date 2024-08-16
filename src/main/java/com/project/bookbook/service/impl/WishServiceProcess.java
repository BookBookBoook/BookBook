package com.project.bookbook.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.project.bookbook.domain.entity.FavoriteBook;
import com.project.bookbook.domain.entity.UserEntity;
import com.project.bookbook.domain.entity.WishEntity;
import com.project.bookbook.domain.repository.FavoriteBookRepository;
import com.project.bookbook.domain.repository.UserRepository;
import com.project.bookbook.domain.repository.WishRepository;
import com.project.bookbook.domain.repository.WishService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class WishServiceProcess implements WishService {
	
	private final UserRepository userRepository;
	private final WishRepository wishRepository;
	private final FavoriteBookRepository favoriteBookRepository;
	
	@Override
	public void addToWishlist(String isbn, long userId) throws Exception {
		//System.out.println("위시리스트 추가 서비스 시작 - ISBN: " + isbn + ", 사용자 ID: " + userId);

        UserEntity user = userRepository.findById(userId)
            .orElseThrow(() -> new Exception("사용자를 찾을 수 없습니다."));

        FavoriteBook favoriteBook = favoriteBookRepository.findById(isbn)
            .orElseThrow(() -> new Exception("책을 찾을 수 없습니다."));

        if (wishRepository.existsByUserAndFavoriteBook(user, favoriteBook)) {
            throw new Exception("이미 위시리스트에 추가된 책입니다.");
        }

        WishEntity wish = WishEntity.builder()
            .user(user)
            .favoriteBook(favoriteBook)
            .build();

        wishRepository.save(wish);
        //System.out.println("위시리스트 추가 완료 - ISBN: " + isbn + ", 사용자 ID: " + userId);
    }
}