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
	
	@GetMapping("/signup-admin")
	public String signupadmin() {
		return "views/login/signup-admin";
	}
	
	@GetMapping("/login-admin")
	public String adminlogin() {
		return "views/login/login-admin";
	}
	
	@GetMapping("/admin-sign")
	public String adminsign() {
		return "views/login/admin-sign";
	}
	
	
	@GetMapping("/test")
	public String test() {
		return "views/login/test";
	}
	
	@GetMapping("/seller")
	public String seller() {
		return "views/login/seller";
	}
	
	@GetMapping("/admin1")
	public String admin() {
		return "views/login/admin";
	}
	
	@GetMapping("/admin/detail")
	public String deatil() {
		return "views/login/admin-detail";
	}
	
	@GetMapping("/refund")
	public String refund() {
		return "views/login/refund";
	}

	@GetMapping("/review")
	public String review() {
		return "views/login/review";
	}
}
