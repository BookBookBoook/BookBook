package com.project.bookbook.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class OrderController {
	
	@GetMapping("/mypage/orders")
	public String order() {
		return "views/mypage/order";
	}
	
	@GetMapping("/mypage/orders/detail")
	public String orderDetail() {
		return "views/mypage/order-detail";
	}
	
}
