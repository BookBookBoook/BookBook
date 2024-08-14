package com.example.bookbook.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.example.bookbook.domain.dto.InquiryDTO;
import com.example.bookbook.mapper.InquiryMapper;
import com.example.bookbook.service.InquiryService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class InquiryServiceProcess implements InquiryService{
	
	private final InquiryMapper inquiryMapper;
	
	@Override
	public void findAllProcess(Model model) {
		List<InquiryDTO> list = inquiryMapper.findAll();
		model.addAttribute("list",list);
		
	}

	@Override
	public void findAll(Model model, long qnaNum) {
		// TODO Auto-generated method stub
		
	}
	
}
