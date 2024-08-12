package com.example.bookbook.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.example.bookbook.domain.dto.QNADTO;
import com.example.bookbook.security.CustomUserDetails;

@Mapper
public interface QNAMapper {

	List<QNADTO> findAll(@Param("user") CustomUserDetails user);
	
}
