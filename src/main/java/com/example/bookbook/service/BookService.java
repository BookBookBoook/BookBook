package com.example.bookbook.service;

import java.util.List;

import org.springframework.ui.Model;

import com.example.bookbook.domain.dto.BookDTO;

public interface BookService {

	//void searchBooks(String string, Model model);

	List<BookDTO> searchBooks(String query);

	void getDefaultBooks(Model model);



}