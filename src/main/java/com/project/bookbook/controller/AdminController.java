package com.project.bookbook.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.project.bookbook.domain.dto.InquiryCreateDTO;
import com.project.bookbook.service.AdminService;
import com.project.bookbook.service.CustomerService;
import com.project.bookbook.service.InquiryService;
import com.project.bookbook.service.InventoryService;


@RequiredArgsConstructor
@Controller
public class AdminController {

	private final InquiryService inquiryService;
	private final InventoryService inventoryService;
	private final CustomerService customerService;
	private final AdminService adminService;
	
	@GetMapping("/admin")
	public String admin(Model model) {
		adminService.find(model);
		return "views/admin/index";
	}
	
	//상품
	
	@GetMapping("/admin/inventory")
	public String adminInventory(Model model) {
		inventoryService.findBook(model);
		return "views/admin/inventory";	
	}
	
	@DeleteMapping("/admin/inventory/{bookNum}")
	public String deleteInventory(@PathVariable("bookNum") long bookNum) {
		inventoryService.deleteProcess(bookNum);
		return "redirect:/admin/inventory";
	}
	
	@GetMapping("/admin/inventory/approval")
	public String adminInventoryApproval(Model model) {
		inventoryService.findBook(model);
		return "views/admin/inventory-approval";	
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
	public String adminUser(Model model) {
		customerService.findCustomer(model);
		return "views/admin/users";
	}
	@GetMapping("/admin/users/detail/{userId}")
	public String adminUserDetail(@PathVariable("userId") long userId, Model model) {
		customerService.findCustomerDetail(model,userId);
		return "views/admin/users-detail";
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
	
	@GetMapping("/admin/inquiry/write/{qnaNum}")
	public String adminInquiryWrite(@PathVariable("qnaNum") long qnaNum, Model model) {
		inquiryService.findAll(model, qnaNum);
		return "views/admin/inquiry-write";
	}
	@PostMapping("/admin/inquiry/write/{qnaNum}")
	public String adminInquiryCreate(InquiryCreateDTO dto) {
		inquiryService.saveProcess(dto);
		return "redirect:/admin/inquiry";
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