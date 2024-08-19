package com.project.bookbook.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.project.bookbook.security.CustomUserDetails;
import com.project.bookbook.service.MypageUserService;
import com.project.bookbook.service.OrderService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class OrderController {
	
	private final OrderService orderService;
	private final MypageUserService userService;
	
	@GetMapping("/mypage/orders")
	public String order() {
		return "views/mypage/order";
	}
	
	//주문 상세
	@GetMapping("/mypage/orders/detail/{merchantUid}")
	public String orderDetail(@PathVariable("merchantUid") long merchantUid, Model model, @AuthenticationPrincipal CustomUserDetails user) {
		userService.readProcess(model, user);
		orderService.findOrdersInfo(model, merchantUid);
		//orderService.findOrdersBy
		return "views/mypage/order-detail";
	}
	
}
