package com.project.bookbook.service;

import org.springframework.ui.Model;

public interface InquiryService {

	void findAllProcess(Model model);

	void findAll(Model model, long qnaNum);

}
