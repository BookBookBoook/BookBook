package com.example.bookbook.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.bookbook.domain.dto.BookDTO;
import com.example.bookbook.service.BookService;

import lombok.RequiredArgsConstructor;


@Controller
@RequestMapping("/")
@RequiredArgsConstructor
public class IndexController {

	// 책 서비스
	@Autowired
	private final BookService bookservice;

	// 메인페이지 이동
	@GetMapping
	public String main() {
		return "views/index/index.html";
	}

	@GetMapping("/bookList")
    public String listBooks(Model model) {
		bookservice.getDefaultBooks(model);
        return "views/index/serchBookList";
    }

	/*
	 * @GetMapping("/search") public String search(@RequestParam("query") String
	 * query, Model model) { bookservice.searchBooks(query, model); return
	 * "views/index/serchBookList.html"; }
	 */
	@GetMapping("/search")
    public String searchBooks(@RequestParam String query, Model model) {
        List<BookDTO> books = bookservice.searchBooks(query);
        model.addAttribute("books", books);
        return "searchResults";
    }
	
	// 도서상세페이지
	@GetMapping("/detail")
	public String detail() {
		return "views/index/detail.html";
	}

	// 이벤트페이지
	@GetMapping("/event")
	public String event() {
		return "views/index/event.html";
	}

}