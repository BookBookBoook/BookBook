package com.example.bookbook.domain.dto;

import com.example.bookbook.domain.entity.SellerEntity;

import lombok.Setter;
import lombok.ToString;

@ToString
@Setter
public class SellerSaveDTO {
	
	 private String shopName;
	    private String telNum;
	    private String businessNum;
	    private String businessReg;
	    private String bank;
	    private String account;
	    private String accountHolder;
	    private String settlementAmount;
	    private String businessRegCopy;
	    

	    public SellerEntity toSellerEntity() {
	        return SellerEntity.builder()
	            .shopName(shopName)
	            .telNum(telNum)
	            .businessNum(businessNum)
	            .businessReg(businessReg)
	            .bank(bank)
	            .account(account)
	            .accountHolder(accountHolder)
	            .settlementAmount(settlementAmount)
	            .businessRegCopy(businessRegCopy)
	            .build();
	    }
	}