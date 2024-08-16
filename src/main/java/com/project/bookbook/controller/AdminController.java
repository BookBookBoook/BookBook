package com.project.bookbook.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import com.project.bookbook.service.InquiryService;


@RequiredArgsConstructor
@Controller
public class AdminController {

	private final InquiryService inquiryService;
	
	@GetMapping("/admin")
	public String admin() {
		return "views/admin/index";
	}
	
	//상품
	@GetMapping("/admin/inventory")
	public String adminInventory() {
		return "views/admin/inventory";	
	}
	@GetMapping("/admin/inventory/write")
	public String adminInventoryWrite() {
		return "views/admin/inventory-write";
	}
	
	
	
	//주문
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
	
	
	//사용자 관리
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

	@GetMapping("/admin/sellers/sign")
	public String adminSellerSign() {
		return "views/admin/sign";
	}
	
	
	
	
	//문의
	@GetMapping("/admin/inquiry")
	public String adminInquiry(Model model) {
		inquiryService.findAllProcess(model);
		return "views/admin/inquiry";
	}
	@GetMapping("/admin/inquiry/list/{qnaNum}")
	public String adminInquiryList(@PathVariable("qnaNum") long qnaNum, Model model) {
		inquiryService.findAll(model,qnaNum);
		return "views/admin/inquiry-list";
	}
	
	@GetMapping("/admin/inquiry/write")
	public String adminInquiryWrite() {
		return "views/admin/inquiry-write";
	}
	
	
	
	//리뷰
	@GetMapping("/admin/review")
	public String adminReview() {
		return "views/admin/review";
	}
	@GetMapping("/admin/review/complain")
	public String adminReviewComplain() {
		return "views/admin/complain";
	}
	
	
	//프로모션
	@GetMapping("/admin/promotion")
	public String adminPromotion() {
		return "views/admin/promotion";
	}
	@GetMapping("/admin/promotion/write")
	public String adminPromotionWrite() {
		return "views/admin/promotion-write";
	}
	
	
}