package com.example.bookbook.service.impl;

import org.springframework.stereotype.Service;

import com.example.bookbook.domain.entity.BookEntity;
import com.example.bookbook.domain.repository.FavoritesRepository;
import com.example.bookbook.domain.repository.UserRepository;
import com.example.bookbook.service.FavoritesService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class FavoritesServiceProcess implements FavoritesService {

	private final FavoritesRepository favoritesRepository;
	private final NaverBookApiService naverBookApiService;

	

}
