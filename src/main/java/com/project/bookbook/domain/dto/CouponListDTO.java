package com.project.bookbook.domain.dto;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CouponListDTO {
	
	private long couponNum;
	private String couponName;
	private int couponRate;
	private String couponDetail;
	private LocalDateTime startDate;
	private LocalDateTime endDate;
	
	public long remainingDays() {
		return ChronoUnit.DAYS.between(LocalDateTime.now(), endDate);
	}
}
