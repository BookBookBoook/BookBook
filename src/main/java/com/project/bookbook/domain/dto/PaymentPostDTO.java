package com.project.bookbook.domain.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PaymentPostDTO {
	
	@JsonProperty("merchant_uid")
	private long merchantUid;
	
	@JsonProperty("paid_amount")
	private int paidAmount;
	
	@JsonProperty("card_name")
	private String cardName;
	
	@JsonProperty("card_number")
	private String cardNumber;

}
