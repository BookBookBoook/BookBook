package com.example.bookbook.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.example.bookbook.domain.dto.QNAAnswerDTO;
import com.example.bookbook.domain.dto.QNACreateDTO;
import com.example.bookbook.domain.dto.QNADTO;
import com.example.bookbook.mapper.QNAMapper;
import com.example.bookbook.security.CustomUserDetails;
import com.example.bookbook.service.QNAService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class QNAServiceProcess implements QNAService{
	
	private final QNAMapper qnaMapper;

	@Override
	public void findAllProcess(Model model, CustomUserDetails user) {
		long userId = user.getUserId();
		List<QNADTO> list = qnaMapper.findAll(userId);
		model.addAttribute("list", list);
		
	}

	@Override
	public void findProcess(Model model, long qnaNum) {
		List<QNADTO> qnas = qnaMapper.findQna(qnaNum);
		List<QNAAnswerDTO> answers = qnaMapper.findAnswer(qnaNum);
		model.addAttribute("qnas", qnas);
		model.addAttribute("answers", answers);
	}
	
	@Override
	public void saveProcess(QNACreateDTO dto) {
		qnaMapper.save(dto);
	}


}
