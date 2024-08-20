package com.project.bookbook.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import com.project.bookbook.domain.dto.AdminIndexDTO;
import com.project.bookbook.domain.dto.ReviewDTO;

@Mapper
public interface AdminMapper {

	@Select("select * from qna order by qna_num desc")
	List<AdminIndexDTO> find();

	List<ReviewDTO> findAllReviews();
}
