package com.project.bookbook.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.project.bookbook.domain.dto.AdminIndexDTO;
import com.project.bookbook.domain.dto.ReviewDTO;
import com.project.bookbook.mapper.AdminMapper;
import com.project.bookbook.service.AdminService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AdminServiceProcess implements AdminService{
	
	private final AdminMapper adminMapper;
	
	@Override
	public void find(Model model) {
		List<AdminIndexDTO> list = adminMapper.find();
		model.addAttribute("list",list);
		
	}

	@Override
	public void findReviews(Model model) {
		List<ReviewDTO> reviews = adminMapper.findAllReviews();
        model.addAttribute("reviews", reviews);
		
	}

}
