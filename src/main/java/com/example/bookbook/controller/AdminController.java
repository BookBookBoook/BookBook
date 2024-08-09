package com.example.bookbook.controller;

import org.springframework.stereotype.Controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@RequiredArgsConstructor
@Controller
public class AdminController {

	@GetMapping("/admin")
	public String admin() {
		return "views/admin/index";
	}
	
	@GetMapping("/admin/inventory")
	public String inventory() {
		return "views/admin/inventory";
	}
	
	@GetMapping("/admin/order")
	public String order() {
		return "views/admin/order";
	}
	
	@GetMapping("/admin/order/refund")
	public String orderRefund() {
		return "views/admin/refund";
	}
	
	@GetMapping("/admin/order/exchange")
	public String orderexchange() {
		return "views/admin/exchange";
	}
	
	
	@GetMapping("/admin/users")
	public String user() {
		return "views/admin/users";
	}
	
	@GetMapping("/admin/users/detail")
	public String userDetail() {
		return "views/admin/users-detail";
	}
	
	@GetMapping("/admin/sellers")
	public String seller() {
		return "views/admin/sellers";
	}
	
	@GetMapping("/admin/sellers/detail")
	public String sellerDetail() {
		return "views/admin/sellers-detail";
	}
	
	@GetMapping("/admin/sellers/inventory/write")
	public String sellerwrite() {
		return "views/admin/write";
	}
	
}
