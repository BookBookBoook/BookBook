/*
package com.example.bookbook.domain.entity;


import org.hibernate.annotations.ColumnDefault;

import com.example.bookbook.domain.entity.Role;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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
@Table(name = "user")
public class UserEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long userId; // 사용자ID

	@Column(nullable = false)
	private String userName; // 사용자이름

	@Column(nullable = false)
	private String userRRN; // 주민등록번호

	@Column(nullable = false)
	private String gender; // 성별
	
	@Column(nullable = false)
	private String email; // 이메일

	@Column(nullable = false)
	private String phoneNumber; // 핸드폰번호

	@Column(nullable = false)
	private String password; // 비밀번호

	// ROLE 권한
	@Enumerated(EnumType.STRING) // DB저장 데이터타입 > 문자값으로 저장(DB에 저장될때 role은 string으로 저장 >> default는 숫자)
	@CollectionTable(name = "role", // RoleEntity 이름(물리적)
			joinColumns = @JoinColumn(name = "userId")) // collection을 entity로 만든다는 것. 여기서 name은 FK컬럼명
	@ElementCollection(fetch = FetchType.EAGER) // 1:N MemberEntity에서만 접근 가능한 내장테이블
	@Builder.Default // builder의 디폴트 값으로 자동 초기화
	@Column(name = "role") // roles의 컬럼명을 role로 바꿈
	private Set<Role> roles = new HashSet<Role>();
	
	@ColumnDefault("true")
	private boolean emailAgree;
	
	@ColumnDefault("true")
	private boolean smsAgree;
	
	@ColumnDefault("0")
	private int point;
	
	@ManyToOne // FK 단방향
	@JoinColumn(name = "sellerId", nullable = true)
	private SellerEntity seller; 
	
	@Column(nullable=true)
	@ColumnDefault("0")
	private int status;
}

*/