package com.example.bookbook.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
public class SellerController {

	
	@GetMapping("/seller")
	public String seller() {
		return "views/seller/index";
	}
	@GetMapping("/seller/inventory")
	public String sellerInventory() {
		return "views/seller/inventory";
	}
	@GetMapping("/seller/inventory/write")
	public String sellerInventoryWrite() {
		return "views/seller/inventory-write";
	}
	@GetMapping("/seller/exchange")
	public String sellerExchange() {
		return "views/seller/exchange";
	}
	
	@GetMapping("/seller/order")
	public String sellerOrder() {
		return "views/seller/order";
	}
	@GetMapping("/seller/refund")
	public String sellerRefund() {
		return "views/seller/refund";
	}
	@GetMapping("/seller/mypage")
	public String sellerMypage() {
		return "views/seller/mypage";
	}
	@GetMapping("/seller/review")
	public String sellerReview() {
		return "views/seller/review";
	}
}
