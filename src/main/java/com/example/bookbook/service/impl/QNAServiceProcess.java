package com.example.bookbook.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

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
		List<QNADTO> list = qnaMapper.findAll(user);
		model.addAttribute("list", list);
		
	}

}
