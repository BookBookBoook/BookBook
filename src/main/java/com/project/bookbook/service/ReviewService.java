package com.project.bookbook.service;

import java.util.List;

import com.project.bookbook.domain.dto.ReviewDTO;

public interface ReviewService {
	
	List<ReviewDTO> getReviewsByIsbn(String isbn);

	ReviewDTO createReview(long userId, String isbn, String content, int rate);


}
