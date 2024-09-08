package com.project.bookbook.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.project.bookbook.domain.dto.BookDTO;
import com.project.bookbook.domain.dto.MypageReviewDTO;

@Mapper
public interface MypageReviewMapper {

	List<String> findAllReview(@Param("randomBookNum") long randomBookNum);

	List<MypageReviewDTO> findReviewByUserId(long userId);

	List<String> findReviewContentsByUserId(long userId);

	List<String> findReviewBooksByUserId(long userId);

}
