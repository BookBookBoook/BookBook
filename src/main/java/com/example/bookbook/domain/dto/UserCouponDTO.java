package com.example.bookbook.domain.dto;

import com.example.bookbook.domain.entity.CouponEntity;
import com.example.bookbook.domain.entity.UserEntity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserCouponDTO {
	
	private UserEntity user;
	private CouponEntity coupon ;

}
