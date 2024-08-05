package com.example.bookbook.controller;

import org.springframework.stereotype.Controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@RequiredArgsConstructor
@Controller
public class AdminController {

	@GetMapping("/admin")
	public String getMethodName() {
		return "views/admin/index";
	}
	
	
}
