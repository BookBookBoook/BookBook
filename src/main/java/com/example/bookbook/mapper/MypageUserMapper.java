package com.example.bookbook.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.example.bookbook.domain.dto.MypageUserDTO;

@Mapper
public interface MypageUserMapper {

	MypageUserDTO findById(long userId);

}
