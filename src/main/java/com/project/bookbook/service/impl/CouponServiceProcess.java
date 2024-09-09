package com.project.bookbook.service.impl;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.stream.Collectors;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.project.bookbook.domain.dto.CouponDTO;
import com.project.bookbook.domain.dto.CouponListDTO;
import com.project.bookbook.domain.entity.BookEntity;
import com.project.bookbook.domain.entity.CouponEntity;
import com.project.bookbook.domain.repository.BookRepository;
import com.project.bookbook.mapper.CouponMapper;
import com.project.bookbook.security.CustomUserDetails;
import com.project.bookbook.service.CouponService;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CouponServiceProcess implements CouponService{
	
	private final CouponMapper couponMapper;
	private final BookRepository bookRepository;

	@Override
	public void findProcess(Model model, CustomUserDetails user) {
		long userId = user.getUserId();
		LocalDateTime now = LocalDateTime.now();
		
		//현재 날짜 기준으로 만료되지 않고, 사용되지 않은 쿠폰만 필터링
		List<CouponListDTO> coupons = couponMapper.findAll(userId);
		List<CouponListDTO> possibleCoupons = coupons.stream()
			.filter(coupon -> ! coupon.getEndDate().isBefore(now))
			.collect(Collectors.toList());
		model.addAttribute("coupons", possibleCoupons);
		
		//현재 날짜 기준으로 만료되거나 사용된 쿠폰만 필터링
		List<CouponListDTO> expiredCoupons = coupons.stream()
				.filter(coupon -> coupon.getEndDate().isBefore(now))
				.collect(Collectors.toList());
		List<CouponListDTO> impossibleCoupons = couponMapper.findAllStatus1(userId);
		for(int i=0; i<impossibleCoupons.size(); i++) {
			expiredCoupons.add(impossibleCoupons.get(i));
		}
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
	public boolean checkDuplicateCoupon(long couponNum, CustomUserDetails user) {
		long userId = user.getUserId();
		Map<String, Long> params = new HashMap<>();
		params.put("userId", userId);
		params.put("couponNum", couponNum);
		return couponMapper.checkDuplicateCoupon(params).isEmpty();
	}

	@Override
	public int findByCouponNum(long couponNum) {
		return couponMapper.findByCouponNum(couponNum);
		
	}
	
	//쿠폰 사용처리
	@Override
	@Transactional
	public void couponStatusChange(long couponNum, CustomUserDetails user) {
		long userId = user.getUserId();
		Map<String, Long> params = new HashMap<>();
		params.put("userId", userId);
		params.put("couponNum", couponNum);
		
		couponMapper.changeStatus(params);
		
	}

	@Override
	public void deleteUserCoupon(long couponNum, CustomUserDetails user) {
		long userId = user.getUserId();
		Map<String, Long> params = new HashMap<>();
		params.put("userId", userId);
		params.put("couponNum", couponNum);
		couponMapper.deleteByCouponNumAndUserId(params);
		
	}
	
	
	//마이페이지 '나의 취향'에서 쿠폰 추가
	@Override
	public void addCouponProcess(String isbn, CustomUserDetails user) {
		
		BookEntity book = bookRepository.findByIsbn(isbn).orElseThrow();
		String bookName = book.getBookName();
		
		int[] discountExample = {500, 1000, 2000, 3000};
		Random ran = new Random();
		ran.nextInt(discountExample.length);
		
		int discount = discountExample[ran.nextInt(discountExample.length)];
		LocalDateTime now = LocalDateTime.now();
		long couponNum = generateRandomId();
		
		CouponDTO couponDTO = CouponDTO.builder()
				.couponNum(couponNum)
				.couponName(discount+"원 할인")
				.couponRate(discount)
				.couponDetail(bookName+" 포함 구매 시 "+discount+"원 할인쿠폰")
				.startDate(now)
				.endDate(now.plusMonths(1))
				.build();
		
		couponMapper.saveCoupon(couponDTO);
		
		long userId = user.getUserId();
		Map<String, Long> params = new HashMap<>();
		params.put("couponNum", couponNum);
		params.put("userId", userId);
		couponMapper.save(params);
		
	}
	
	
	public long generateRandomId() {
        Random random = new Random();
        long min = 100_000_000_000L; // 12자리 숫자의 최소값
        long max = 999_999_999_999L; // 12자리 숫자의 최대값
        
        return min + ((long) (random.nextDouble() * (max - min)));
    }

}
