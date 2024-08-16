package com.project.bookbook.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import com.project.bookbook.security.CustomUserDetails;
import com.project.bookbook.service.CartService;

@Controller
@RequiredArgsConstructor
public class CartController {
	
	private final CartService cartService;
	
	@GetMapping("/cart")
	public String cartList(Model model, @AuthenticationPrincipal CustomUserDetails user) {
		cartService.findAllProcess(model, user);
		return "views/cart/cart-list";
	}
	
	@GetMapping("/payment")
	public String payment() {
		return "views/cart/payment";
	}
	
	@GetMapping("/payment/completion")
	public String paymentCompletion() {
		return "views/cart/completion";
	}
	

}
