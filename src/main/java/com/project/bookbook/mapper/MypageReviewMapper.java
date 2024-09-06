package com.project.bookbook.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.project.bookbook.domain.dto.BookDTO;

@Mapper
public interface MypageReviewMapper {

	List<String> findAllReview(@Param("randomBookNum") long randomBookNum);

}
