package com.example.bookbook.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import com.example.bookbook.domain.dto.KakaoBookDto;

@Mapper
public interface BookMapper {
	
	@Select("SELECT * FROM books")
	List<KakaoBookDto> selectAll();

}
