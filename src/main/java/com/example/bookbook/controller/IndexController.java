package com.example.bookbook.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
@RequestMapping("/")
public class IndexController {
	
	@GetMapping
	public String main() {
		return "views/index/index.html";
	}
	@GetMapping("/bookList")
	public String list() {
		return "views/index/bookList.html";
	}
	
	
}
