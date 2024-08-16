package com.example.bookbook.domain.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;

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
@Table(name = "orders")
public class OrdersEntity{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long orderNum; //주문번호
	
	@ManyToOne // FK 단방향
	@JoinColumn(name = "userId", nullable = false)
	private UserEntity user; // 사용자ID fk
	
	@ManyToOne // FK 단방향
	@JoinColumn(name = "bookNum", nullable = false)
	private BookEntity book ; // 도서번호 fk
	
	@CreationTimestamp
	@Column(columnDefinition = "timestamp")
	private LocalDateTime orderDate; //주문날짜
	
	private long orderTotal; //총 결제금액
	
	private String orderMethod; //결제수단
	
	@Column(nullable=true)
	@ColumnDefault("0")
	private String orderStatus; //주문상태
	//0:주문대기, 1:주문중, 2:배송중, 3:배송완료, 4:환불, 5:교환, 6:취소
	
	private String field; //환불사유
	
	private String exchangeReason; //교환사유
	
}
