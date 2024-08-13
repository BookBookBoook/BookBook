package com.example.bookbook.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.bookbook.domain.dto.QNACreateDTO;
import com.example.bookbook.security.CustomUserDetails;
import com.example.bookbook.service.QNAService;


@Controller
@RequiredArgsConstructor
public class MypageController {
	
	private final QNAService qnaService;
	
	//회원정보
	@GetMapping("/mypage/account")
	public String account() {
		return "views/mypage/account";
	}
	
	@GetMapping("/mypage/account/delete")
	public String accountDel() {
		return "views/mypage/account-delete";
	}
	
	//1:1 문의
	@GetMapping("/mypage/questions")
	public String question(Model model, @AuthenticationPrincipal CustomUserDetails user) {
		qnaService.findAllProcess(model, user);
		return "views/mypage/question";
	}
	
	@GetMapping("/mypage/questions/detail")
	public String questionDetail() {
		return "views/mypage/question-detail";
	}
	
	@PostMapping("/mypage/questions/detail")
	public String qnaCreate(QNACreateDTO dto) {
		qnaService.saveProcess(dto);
		return "redirect:/mypage/questions";
	}
	
	@GetMapping("/mypage/questions/{qnaNum}")
	public String questionForm(@PathVariable("qnaNum") long qnaNum, Model model) {
		qnaService.findProcess(model, qnaNum);
		return "views/mypage/question-form";
	}
	
	
	//쿠폰
	@GetMapping("/mypage/coupons")
	public String coupon() {
		return "views/mypage/coupon";
	}
	
	//나의 취향
	@GetMapping("/mypage/favorites")
	public String favorite() {
		return "views/mypage/favorite";
	}

}
