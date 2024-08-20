package com.project.bookbook.service.impl;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.project.bookbook.domain.dto.CouponListDTO;
import com.project.bookbook.domain.entity.CouponEntity;
import com.project.bookbook.mapper.CouponMapper;
import com.project.bookbook.security.CustomUserDetails;
import com.project.bookbook.service.CouponService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CouponServiceProcess implements CouponService{
	
	private final CouponMapper couponMapper;

	@Override
	public void findProcess(Model model, CustomUserDetails user) {
		long userId = user.getUserId();
		LocalDateTime now = LocalDateTime.now();
		
		//현재 날짜 기준으로 만료되지 않은 쿠폰만 필터링
		List<CouponListDTO> coupons = couponMapper.findAll(userId);
		List<CouponListDTO> possibleCoupons = coupons.stream()
			.filter(coupon -> ! coupon.getEndDate().isBefore(now))
			.collect(Collectors.toList());
		model.addAttribute("coupons", possibleCoupons);
		
		//현재 날짜 기준으로 만료된 쿠폰만 필터링
		List<CouponListDTO> expiredCoupons = coupons.stream()
				.filter(coupon -> coupon.getEndDate().isBefore(now))
				.collect(Collectors.toList());
		model.addAttribute("expiredCoupons", expiredCoupons);
	}

	@Override
	public boolean checkProcess(long couponNum) {
		return couponMapper.checkCoupon(couponNum).isEmpty();
	}

	@Override
	public void addProcess(long couponNum, CustomUserDetails user) {
		Map<String, Long> params = new HashMap<String, Long>();
		params.put("couponNum", couponNum);
		long userId = user.getUserId();
		params.put("userId", userId);
		couponMapper.save(params);
		
	}

	@Override
	public boolean checkDuplicateCoupon(long couponNum) {
		return couponMapper.checkDuplicateCoupon(couponNum).isEmpty();
	}

	@Override
	public int findByCouponNum(long couponNum) {
		return couponMapper.findByCouponNum(couponNum);
		
		
	}

}
