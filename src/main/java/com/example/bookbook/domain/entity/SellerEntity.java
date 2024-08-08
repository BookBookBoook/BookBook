/*
package com.example.bookbook.domain.entity;

import org.hibernate.annotations.ColumnDefault;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter // Setter는 주로 안 씀
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "seller") // 별도로 지정하지 않으면 클래스이름이 테이블명
public class SellerEntity {
	
	@Id // PK 컬럼 설정하는 어노테이션
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private long sellerId; // ID

	@Column(nullable = false)
	private String shopName; 

	@Column(nullable = false)
	private String telNum; 
	
	@Column(nullable = false)
	private String business_num; 
	
	@Column(nullable = false)
	private String business_reg; 
	
	@Column(nullable = false)
	private String bank; 
	
	@Column(nullable = false)
	private long account; // ID
	
	@Column(nullable = false)
	private String accountHolder; 
	
	@Column(nullable = false)
	private String settlementAmount; 

	private String businessRegCopy;
	
	@Column(nullable=true)
	@ColumnDefault("false")
	private boolean approval;
	
}
*/