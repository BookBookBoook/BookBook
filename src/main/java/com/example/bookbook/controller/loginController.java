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
	
	@GetMapping("/login/admin")
	public String adminlogin() {
		return "views/login/login-admin";
	}
	
	@GetMapping("/admin/sign")
	public String adminsign() {
		return "views/login/admin-sign";
	}
	
	@GetMapping("/test")
	public String test() {
		return "views/login/test";
	}
	

}
