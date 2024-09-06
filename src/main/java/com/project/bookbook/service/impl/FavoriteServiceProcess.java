package com.project.bookbook.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.project.bookbook.domain.dto.FavoriteListDTO;
import com.project.bookbook.mapper.FavoriteMapper;
import com.project.bookbook.security.CustomUserDetails;
import com.project.bookbook.service.FavoriteService;
import com.project.bookbook.service.ReviewService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class FavoriteServiceProcess implements FavoriteService{
	
	private final FavoriteMapper favoriteMapper;
	private final MypageReviewProcess reviewService;
	
	@Override
	public void findByUser(Model model, CustomUserDetails user) {
		long userId = user.getUserId();
		List<FavoriteListDTO> favorites = favoriteMapper.findByUser(userId);
		model.addAttribute("favorites", favorites);
		
		Random ran = new Random();
		int randomInt = ran.nextInt(favorites.size());
		long randomBookNum = favorites.get(randomInt).getBookNum();
		
		String summaryReview = reviewService.getReviewsSummaryAI(randomBookNum);
		if(summaryReview != null) {
			model.addAttribute("randomBook",favorites.get(randomInt));
			model.addAttribute("summaryReview", summaryReview);
		}
		
	}

	@Override
	public void deleteProcess(long bookNum, CustomUserDetails user) {
		Map<String, Long> params = new HashMap<>();
		params.put("userId", user.getUserId());
		params.put("bookNum", bookNum);
		favoriteMapper.deleteFavorite(params);
		
	}

}
