package com.example.bookbook.service;

import org.springframework.ui.Model;

import com.example.bookbook.domain.dto.accountUpdateDTO;
import com.example.bookbook.security.CustomUserDetails;

public interface MypageUserService {

	void readProcess(Model model, CustomUserDetails user);

	void updateProcess(accountUpdateDTO dto);

}
