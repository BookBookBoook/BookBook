package com.example.bookbook.service;

import org.springframework.ui.Model;

import com.example.bookbook.security.CustomUserDetails;

public interface QNAService {

	void findAllProcess(Model model, CustomUserDetails user);

}
