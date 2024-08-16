package com.project.bookbook.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

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
	
	@DeleteMapping("/cart/{cartDetailNum}")
	public void cartDetailDelete(@PathVariable("cartDetailNum") long cartDetailNum) {
		cartService.deleteCartDetail(cartDetailNum);
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
