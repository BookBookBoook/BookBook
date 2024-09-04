package com.project.bookbook.service;

import com.project.bookbook.domain.dto.bot.QuestionDTO;

public interface OpenaiService {

	String aiAnswerProcess(QuestionDTO questionDTO);

}
