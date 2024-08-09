package com.example.bookbook.domain.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "publisher") // 테이블명을 "publisher"로 지정
public class PublisherEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long publisherId; // ID

    @Column(nullable = false)
    private String shopName; // 출판사 이름

    @Column(nullable = false)
    private String telNum; // 전화번호

    @Column(nullable = false)
    private String businessNum; // 사업자 번호

    @Column(nullable = false)
    private String businessReg; // 사업자 등록증

    @Column(nullable = false)
    private String bank; // 은행명

    @Column(nullable = false)
    private long account; // 계좌 번호

    @Column(nullable = false)
    private String accountHolder; // 계좌 소유자

    @Column(nullable = false)
    private String settlementAmount; // 정산 금액

    private String businessRegCopy; // 사업자 등록증 사본

	/*
	 * @Column(nullable = true)
	 * 
	 * @ColumnDefault("false") private boolean approval; // 승인 여부
	 */
    // 추가적인 메서드를 필요에 따라 추가할 수 있습니다.
}
