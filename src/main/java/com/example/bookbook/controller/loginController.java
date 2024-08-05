package com.example.bookbook.controller;

import org.springframework.stereotype.Controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
@RequiredArgsConstructor
public class loginController {
	
	@GetMapping("/login")
	public String login() {
		return "views/login/login";
	}
	
	@GetMapping("/signup")
	public String signup() {
		return "views/login/signup";
	}
}
