package com.example.bookbook.controller;

import org.springframework.stereotype.Controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
@RequiredArgsConstructor
public class MypageController {
	
	@GetMapping("/mypage/orders")
	public String mypage() {
		return "views/mypage/order";
	}
	

}
