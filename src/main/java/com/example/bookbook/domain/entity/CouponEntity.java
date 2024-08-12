package com.example.bookbook.domain.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "coupon")
public class CouponEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long couponNum; // 쿠폰 번호
	
	@Column(nullable=false)
	private String couponName; //쿠폰 이름
	
	private int couponRate; //할인율
	
	private String couponDetail; //쿠폰 상세정보
	
	@Column(columnDefinition = "timestamp")
	private LocalDateTime startDate; //쿠폰 시작일
	
	@Column(columnDefinition = "timestamp")
	private LocalDateTime endDate; //쿠폰 만료일

}
