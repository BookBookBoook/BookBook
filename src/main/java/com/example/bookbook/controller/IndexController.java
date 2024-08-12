package com.example.bookbook.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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

	@GetMapping("/search")
	public String search(@RequestParam("query") String query, Model model) {
		bookservice.searchBooks(query, model);
		return "views/index/serchBookList.html";
	}

	/*
	 * @GetMapping("/search") public String searchBooks(@RequestParam(required =
	 * false, name ="query") String query,
	 * 
	 * @RequestParam(required = false, defaultValue = "통합검색") String searchType,
	 * Model model) { bookservice.searchBooks(query, searchType, model); return
	 * "searchResults"; }
	 */

	@GetMapping("/detail/{isbn}")
	public String detail(@PathVariable("isbn") String isbn, Model model) {
		try {
			BookDTO book = bookservice.getBookByIsbn(isbn);
			if (book != null) {
				model.addAttribute("book", book);
			} else {
				model.addAttribute("error", "도서를 찾을 수 없습니다.");
			}
		} catch (Exception e) {
			model.addAttribute("error", "도서 정보를 가져오는 중 오류가 발생했습니다.");
		}
		return "views/index/detail";
	}

	// 이벤트페이지
	@GetMapping("/event")
	public String event() {
		return "views/index/event.html";
	}

}