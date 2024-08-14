package com.example.bookbook.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.bookbook.domain.dto.QNACreateDTO;
import com.example.bookbook.domain.dto.accountUpdateDTO;
import com.example.bookbook.security.CustomUserDetails;
import com.example.bookbook.service.MypageUserService;
import com.example.bookbook.service.QNAService;


@Controller
@RequiredArgsConstructor
public class MypageController {
	
	private final QNAService qnaService;
	private final MypageUserService userService;
	
	//회원정보
	@GetMapping("/mypage/account")
	public String account(Model model, @AuthenticationPrincipal CustomUserDetails user) {
		userService.readProcess(model, user);
		return "views/mypage/account";
	}
	
	@PutMapping("/mypage/account")
	public String accountUpdate(accountUpdateDTO dto) {
		userService.updateProcess(dto);
		return "redirect:/mypage/account";
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
	public String qnaForm(@PathVariable("qnaNum") long qnaNum, Model model) {
		qnaService.findProcess(model, qnaNum);
		return "views/mypage/question-form";
	}
	
	@DeleteMapping("/mypage/questions/{qnaNum}")
	public String deleteQna(@PathVariable("qnaNum") long qnaNum) {
		qnaService.deleteProcess(qnaNum);
		return "redirect:/mypage/questions";
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
