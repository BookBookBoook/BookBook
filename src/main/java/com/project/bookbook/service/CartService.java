package com.project.bookbook.service;

import org.springframework.ui.Model;

import com.project.bookbook.security.CustomUserDetails;

public interface CartService {

	void findAllProcess(Model model, CustomUserDetails user);

}
