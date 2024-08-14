package com.example.bookbook.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.example.bookbook.domain.dto.MypageUserDTO;
import com.example.bookbook.domain.dto.accountUpdateDTO;

@Mapper
public interface MypageUserMapper {

	MypageUserDTO findById(long userId);

	void updateId(accountUpdateDTO dto);

}
