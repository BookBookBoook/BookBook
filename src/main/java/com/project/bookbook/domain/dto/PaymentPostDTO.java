package com.project.bookbook.domain.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PaymentPostDTO {
	
	private long merchantUid;
	private long couponNum;
	private int amount;
	private int couponRate;
	private int finalAmount;

}
