package com.example.bookbook.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.bookbook.service.BookService;

import lombok.RequiredArgsConstructor;


@Controller
@RequestMapping("/")
@RequiredArgsConstructor
public class IndexController {
	
	//책 서비스
	private final BookService bookService;
	
	//메인페이지 이동
	@GetMapping
	public String main() {
		return "views/index/index.html";
	}
	
	//검색페이지
	@GetMapping("/search")
	public String searchBooks(@RequestParam String query,int page,int size, Model model) {
		bookService.searchBooks(query,page,size,model);
		return "views/index/searchResults";
	}
	//검색
	
	//도서목록페이지
	@GetMapping("/bookList")
	public String listBooks(Model model) {
		bookService.getAllBooks(model);
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