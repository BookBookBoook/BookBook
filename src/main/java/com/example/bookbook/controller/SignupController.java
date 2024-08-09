package com.example.bookbook.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.bookbook.domain.dto.UserSaveDTO;
import com.example.bookbook.service.UserService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
public class SignupController {
	
	private final UserService service;
	
	@GetMapping("/signup")
	public String signup() {
		return "views/login/signup";
	}
	
	@PostMapping("/signup")
	public String signup(UserSaveDTO dto) {
		service.signupProcess(dto);
		return "redirect:/";
	}
	
	@GetMapping("/signup-admin")
	public String signupadmin() {
		return "views/login/signup-admin";
	}

}
