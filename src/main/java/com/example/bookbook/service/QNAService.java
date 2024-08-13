package com.example.bookbook.service;

import org.springframework.ui.Model;

import com.example.bookbook.domain.dto.QNACreateDTO;
import com.example.bookbook.security.CustomUserDetails;

public interface QNAService {

	void findAllProcess(Model model, CustomUserDetails user);

	void saveProcess(QNACreateDTO dto);

	void findProcess(Model model, long qnaNum);

	void deleteProcess(long qnaNum);

}
