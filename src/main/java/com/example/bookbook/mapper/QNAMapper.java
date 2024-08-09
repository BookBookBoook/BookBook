package com.example.bookbook.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.example.bookbook.domain.dto.QNADTO;

@Mapper
public interface QNAMapper {

	List<QNADTO> findAll();
	
}
