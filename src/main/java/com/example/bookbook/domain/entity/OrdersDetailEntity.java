package com.example.bookbook.domain.entity;

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
@Table(name = "orders_detail")
public class OrdersDetailEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long orderDetailNum; //주문상세번호
	
	@ManyToOne // FK 단방향
	@JoinColumn(name = "orderNum", nullable = false)
	private OrdersEntity orders; // 사용자ID fk
	
	@ManyToOne // FK 단방향
	@JoinColumn(name = "itemKey", nullable = false)
	private BookEntity book ; // 도서번호 fk
	
	private long orderCnt; //주문수량 
	
}
