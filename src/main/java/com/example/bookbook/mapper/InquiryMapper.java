package com.example.bookbook.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.example.bookbook.domain.dto.InquiryDTO;
import com.example.bookbook.domain.dto.InquiryDetailDTO;

@Mapper
public interface InquiryMapper {

	@Select("select * from qna order by qna_num desc")
	List<InquiryDTO> findAll();

	@Select("select * from qna where qna_num=#{qnaNum}")
	InquiryDTO findInquiry(@Param("qnaNum") long qnaNum);

	@Select("select * from qna_answer where qna_num=#{qnaNum}")
	InquiryDetailDTO findDetail(@Param("qnaNum") long qnaNum);

	
}
