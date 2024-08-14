package com.example.bookbook.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import com.example.bookbook.domain.dto.InquiryDTO;

@Mapper
public interface InquiryMapper {

	@Select("select * from qna order by qna_num desc")
	List<InquiryDTO> findAll();
	
}
