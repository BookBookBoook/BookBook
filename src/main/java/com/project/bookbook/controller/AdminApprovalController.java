package com.project.bookbook.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.project.bookbook.domain.entity.SellerEntity;
import com.project.bookbook.service.SellerService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class AdminApprovalController {
	
	 private final SellerService sellerService;

	 @GetMapping("/admin/approve")
	    public String getPendingSellers(Model model) {
	        List<SellerEntity> pendingSellers = sellerService.getPendingSellers();
	        model.addAttribute("pendingSellers", pendingSellers);
	        return "views/login/admin-sign";
	    }

	    @PostMapping("/approve-seller/{sellerId}")
	    public String approveSeller(@PathVariable("sellerId") Long sellerId, RedirectAttributes redirectAttributes) {
	        try {
	            sellerService.approveSeller(sellerId);
	            redirectAttributes.addFlashAttribute("message", "출판사가 승인되었습니다.");
	        } catch (Exception e) {
	            redirectAttributes.addFlashAttribute("error", "승인 중 오류가 발생했습니다: " + e.getMessage());
	        }
	        return "redirect:/admin/approve";
	    }

	    @PostMapping("/reject-seller/{sellerId}")
	    public String rejectSeller(@PathVariable("sellerId") Long sellerId, RedirectAttributes redirectAttributes) {
	        try {
	            sellerService.rejectSeller(sellerId);
	            redirectAttributes.addFlashAttribute("message", "출판사가 거부되었습니다.");
	        } catch (Exception e) {
	            redirectAttributes.addFlashAttribute("error", "거부 중 오류가 발생했습니다: " + e.getMessage());
	        }
	        return "redirect:/admin/approve";
	    }
	}