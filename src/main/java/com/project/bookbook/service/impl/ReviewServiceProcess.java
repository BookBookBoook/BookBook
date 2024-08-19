package com.project.bookbook.service.impl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.project.bookbook.domain.dto.ReviewDTO;
import com.project.bookbook.domain.entity.BookEntity;
import com.project.bookbook.domain.entity.ReviewEntity;
import com.project.bookbook.domain.entity.UserEntity;
import com.project.bookbook.domain.repository.BookRepository;
import com.project.bookbook.domain.repository.ReviewRepository;
import com.project.bookbook.domain.repository.UserRepository;
import com.project.bookbook.service.ReviewService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ReviewServiceProcess implements ReviewService {

	private final ReviewRepository reviewRepository;
	private final UserRepository userRepository;
    private final BookRepository bookRepository;


	/**
    * ReviewEntity를 ReviewDTO로 변환합니다.
    * @param review 변환할 ReviewEntity 객체
    * @return 변환된 ReviewDTO 객체
    */
   private ReviewDTO convertToDTO(ReviewEntity review) {
       return ReviewDTO.builder()
               .reviewNum(review.getReviewNum())
               .username(review.getUser().getUserName())
               .reviewContent(review.getReviewContent())
               .rate(review.getRate())
               //.reviewDate(review.getReviewDate())
               .recommend(review.getRecommend())
               .complaint(review.getComplaint())
               .actualOrder(review.getActualOrder() == 1)
               .build();
   }
	@Override
	@Transactional(readOnly = true)
	public List<ReviewDTO> getReviewsByIsbn(String isbn) {
		List<ReviewEntity> reviews = reviewRepository.findByBookIsbnOrderByReviewDateDesc(isbn);
		return reviews.stream().map(this::convertToDTO).collect(Collectors.toList());
	}

	@Override
	@Transactional
	public ReviewDTO createReview(long userId, String isbn, String content, int rate) {
		UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        
        BookEntity book = bookRepository.findByIsbn(isbn)
                .orElseThrow(() -> new RuntimeException("Book not found"));

        ReviewEntity review = ReviewEntity.builder()
                .user(user)
                .book(book)
                .reviewContent(content)
                .rate(rate)
                //.createdAt(LocalDateTime.now())
                .build();

        ReviewEntity savedReview = reviewRepository.save(review);
        return convertToDTO(savedReview);
	}

}
