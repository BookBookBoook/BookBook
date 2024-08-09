package com.example.bookbook.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.bookbook.service.main.KakaoBookService;

import lombok.RequiredArgsConstructor;


@Controller
@RequestMapping("/")
@RequiredArgsConstructor
public class IndexController {
	//카카오api 서비스
	private final KakaoBookService kakaoBookService;
	
	//메인페이지 이동
	@GetMapping
	public String main() {
		return "views/index/index.html";
	}
	
	//도서목록페이지
	@GetMapping("/bookList")
	public String list() {
		return "views/index/bookList.html";
	}
	//도서상세페이지
	@GetMapping("/detail")
	public String detail() {
		return "views/index/detail.html";
	}
	//이벤트페이지
	@GetMapping("/event")
	public String event() {
		return "views/index/event.html";
	}
	
	
}
