package com.example.bookbook.service;

import org.springframework.ui.Model;

public interface BookService {

	void getAllBooks(Model model);

	void searchBooks(String query,int page, int size, Model model);




}
