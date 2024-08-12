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
	public String adminInventory() {
		return "views/admin/inventory";
	}
	@GetMapping("/admin/order")
	public String adminOrder() {
		return "views/admin/order";
	}
	@GetMapping("/admin/order/refund")
	public String adminOrderRefund() {
		return "views/admin/refund";
	}
	@GetMapping("/admin/order/exchange")
	public String adminOrderExchange() {
		return "views/admin/exchange";
	}
	@GetMapping("/admin/users")
	public String adminUser() {
		return "views/admin/users";
	}
	@GetMapping("/admin/users/detail")
	public String adminUserDetail() {
		return "views/admin/users-detail";
	}
	@GetMapping("/admin/sellers")
	public String adminSeller() {
		return "views/admin/sellers";
	}
	@GetMapping("/admin/sellers/detail")
	public String adminSellerDetail() {
		return "views/admin/sellers-detail";
	}
	@GetMapping("/admin/review")
	public String adminReview() {
		return "views/admin/review";
	}
	@GetMapping("/admin/inquiry")
	public String adminInquiry() {
		return "views/admin/inquiry";
	}
	@GetMapping("/admin/inquiry/list")
	public String adminInquiryList() {
		return "views/admin/inquiry-list";
	}
	@GetMapping("/admin/inquiry/write")
	public String adminInquiryWrite() {
		return "views/admin/inquiry-write";
	}
	@GetMapping("/admin/promotion")
	public String adminPromotion() {
		return "views/admin/promotion";
	}
	@GetMapping("/admin/promotion/write")
	public String adminPromotionWrite() {
		return "views/admin/promotion-write";
	}
	@GetMapping("/admin/inventory/write")
	public String adminInventoryWrite() {
		return "views/admin/inventory-write";
	}
}
