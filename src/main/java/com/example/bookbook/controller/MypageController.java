package com.example.bookbook.controller;

import org.springframework.stereotype.Controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
@RequiredArgsConstructor
public class MypageController {
	
	@GetMapping("/mypage/orders")
	public String order() {
		return "views/mypage/order";
	}
	
	@GetMapping("/mypage/orders/detail")
	public String orderDetail() {
		return "views/mypage/order-detail";
	}
	
	@GetMapping("/mypage/account")
	public String account() {
		return "views/mypage/account";
	}
	
	@GetMapping("/mypage/account/delete")
	public String accountDel() {
		return "views/mypage/account-delete";
	}
	
	@GetMapping("/mypage/questions")
	public String question() {
		return "views/mypage/question";
	}
	
	@GetMapping("/mypage/questions/detail")
	public String questionDetail() {
		return "views/mypage/question-detail";
	}
	
	@GetMapping("/mypage/coupons")
	public String coupon() {
		return "views/mypage/coupon";
	}
	
	@GetMapping("/mypage/favorites")
	public String favorite() {
		return "views/mypage/favorite";
	}

}
