package com.example.bookbook.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;

import com.example.bookbook.domain.dto.MypageUserDTO;
import com.example.bookbook.domain.dto.accountUpdateDTO;
import com.example.bookbook.mapper.MypageUserMapper;
import com.example.bookbook.security.CustomUserDetails;
import com.example.bookbook.service.MypageUserService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MypageUserServiceProcess implements MypageUserService{
	
	private final MypageUserMapper userMapper;
	
	@Override
	public void readProcess(Model model, CustomUserDetails user) {
		long userId = user.getUserId();
		MypageUserDTO userInfo = userMapper.findById(userId);
		model.addAttribute("user", userInfo);
		
	}

	@Override
	@Transactional
	public void updateProcess(accountUpdateDTO dto) {
		userMapper.updateId(dto);
	}

	@Override
	@Transactional
	public void changeStatus(CustomUserDetails user) {
		long userId = user.getUserId();
		userMapper.changeStatus(userId);
		
	}

}
