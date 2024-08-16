package com.example.bookbook.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.example.bookbook.domain.dto.CouponListDTO;
import com.example.bookbook.domain.dto.UserCouponDTO;
import com.example.bookbook.domain.entity.CouponEntity;
import com.example.bookbook.security.CustomUserDetails;

@Mapper
public interface CouponMapper {

	List<CouponListDTO> findAll(long userId);

	List<CouponListDTO> checkCoupon(long couponNum);

	void save(Map<String, Long> params);

	List<UserCouponDTO> checkDuplicateCoupon(long couponNum);

}
