package com.project.bookbook.service;

import org.springframework.ui.Model;

import com.project.bookbook.security.CustomUserDetails;

public interface HistoryService {

	void findAllProcess(CustomUserDetails user, Model model);

	void deleteProcess(CustomUserDetails user);

	void findQueryByUserId(CustomUserDetails user, Model model);

}
