package com.project.bookbook.domain.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CouponDTO {
	
	private long couponNum;
	private String couponName;
	private int couponRate;
	private String couponDetail;
	private LocalDateTime startDate;
	private LocalDateTime endDate;

}
