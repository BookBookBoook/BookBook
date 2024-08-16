package com.project.bookbook.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.project.bookbook.domain.dto.MypageUserDTO;
import com.project.bookbook.domain.dto.accountUpdateDTO;

@Mapper
public interface MypageUserMapper {

	MypageUserDTO findById(long userId);

	void updateId(accountUpdateDTO dto);

	void changeStatus(long userId);

}
