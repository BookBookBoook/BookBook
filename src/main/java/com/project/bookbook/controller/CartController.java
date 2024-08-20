package com.project.bookbook.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.project.bookbook.domain.dto.PaymentAllDTO;
import com.project.bookbook.domain.dto.PaymentPostDTO;
import com.project.bookbook.security.CustomUserDetails;
import com.project.bookbook.service.CartService;
import com.project.bookbook.service.CouponService;
import com.project.bookbook.service.MypageUserService;
import com.project.bookbook.service.OrderService;

import groovy.util.logging.Slf4j;

@Controller
@RequiredArgsConstructor
public class CartController {

	private final CouponService couponService;
	private final CartService cartService;
	private final OrderService orderService;
	private final MypageUserService userService;
	
	//장바구니
	@GetMapping("/cart")
	public String cartList(Model model, @AuthenticationPrincipal CustomUserDetails user) {
		cartService.findAllProcess(model, user);
		return "views/cart/cart-list";
	}
	
	@ResponseBody
	@DeleteMapping("/cart/{cartDetailNum}")
	public ResponseEntity<?> deleteCartItem(@PathVariable("cartDetailNum") Long cartDetailNum) {
		try {
			cartService.deleteCartDetail(cartDetailNum); //장바구니에 담긴 도서 개별 삭제
			ResponseEntity<?> response = ResponseEntity.ok().build();
			return response;
		} catch (Exception e) {
			ResponseEntity<?> response = ResponseEntity.internalServerError().body("삭제 과정에서 오류가 발생했습니다.");
			return response;
		}
	}
	
	@ResponseBody
	@PostMapping("/cart/all")
	public ResponseEntity<Object> paymentAll(@RequestBody PaymentAllDTO request, @AuthenticationPrincipal CustomUserDetails user) {
		if(request.getCartDetailNums().isEmpty()) {
			return ResponseEntity.badRequest().body("장바구니가 비어있습니다.");
			
		}else {
			List<Long> cartDetailNums = request.getCartDetailNums();
			long merchantUid = orderService.createOrder(cartDetailNums, user);
			return ResponseEntity.ok(merchantUid); //long형 orderNum을 직접 반환
		}
	}
	
	//결제
	@GetMapping("/payment/{merchantUid}")
	public String payment(@PathVariable("merchantUid") long merchantUid, Model model, @AuthenticationPrincipal CustomUserDetails user) {
		userService.readProcess(model, user);
		orderService.findOrdersInfo(model, merchantUid);
		couponService.findProcess(model, user);
		return "views/cart/payment";
	}
	
	@PostMapping("/payment/completion")
	@ResponseBody
	public String paymentPost(@RequestBody PaymentPostDTO dto, @AuthenticationPrincipal CustomUserDetails user) {
		dto.setPaidAmount(dto.getAmount() - dto.getCouponRate());
		long merchantUid = dto.getMerchantUid();
		try {
			orderService.orderCompletion(dto);
			cartService.cartEmptyProcess(user); //장바구니 비우기
			
		}catch (Exception e) {
		}
		return "";
	}

	@GetMapping("/payment/completion/{merchantUid}")
	public String paymentCompletion(@PathVariable("merchantUid") long merchantUid, Model model) {
		orderService.findByMerchantUid(merchantUid, model);
		return "views/cart/completion";
	}

}
