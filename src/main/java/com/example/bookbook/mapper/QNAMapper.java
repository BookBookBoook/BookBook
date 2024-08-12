package com.example.bookbook.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.example.bookbook.domain.dto.QNADTO;
import com.example.bookbook.security.CustomUserDetails;

@Mapper
public interface QNAMapper {
	
	@Select("select * from qna where user_id = #{userId} order by qna_num desc")
	List<QNADTO> findAll(@Param("userId") long userId);
	
}
