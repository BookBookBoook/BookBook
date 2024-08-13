package com.example.bookbook.service;

import org.springframework.ui.Model;

import com.example.bookbook.security.CustomUserDetails;

public interface MypageUserService {

	void readProcess(Model model, CustomUserDetails user);

}
