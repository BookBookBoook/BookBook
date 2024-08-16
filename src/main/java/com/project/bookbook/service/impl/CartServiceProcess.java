package com.project.bookbook.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.project.bookbook.domain.dto.CartDetailDTO;
import com.project.bookbook.mapper.CartMapper;
import com.project.bookbook.security.CustomUserDetails;
import com.project.bookbook.service.CartService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CartServiceProcess implements CartService{
	
	private final CartMapper cartMapper;
	
	@Override
	public void findAllProcess(Model model, CustomUserDetails user) {
		long userId = user.getUserId();
		List<CartDetailDTO> cartDetails = cartMapper.findAllCartDetail(userId);
		model.addAttribute("cartDetails", cartDetails);
		
	}

}
