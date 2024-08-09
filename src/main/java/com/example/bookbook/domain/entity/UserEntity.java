package com.example.bookbook.domain.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import org.hibernate.annotations.DynamicUpdate;


import java.util.HashSet;
import java.util.Set;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@DynamicUpdate
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

    @Column(nullable = true)
    private String email; // 이메일 (선택입력)

    @Column(nullable = false)
    private String phoneNumber; // 핸드폰번호

    @Column(nullable = false)
    private String password; // 비밀번호

    @Column(nullable = false)
    private String birthDate; // 생년월일

    @Column(nullable = false)
    private String postcode; // 우편번호

    @Column(nullable = false)
    private String address; // 주소

    @Column(nullable = true)
    private String extraAddress; // 참고항목

    @Column(nullable = false)
    private String detailAddress; // 상세주소
    
    @ManyToOne
    @JoinColumn(name = "sellerId", nullable = true)
    private PublisherEntity seller;

    @Enumerated(EnumType.STRING)
    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "role", joinColumns = @JoinColumn(name = "userId"))
    @Builder.Default
    @Column(name = "role")
    private Set<Role> roles = new HashSet<Role>(); // 'Role' Enum 타입을 별도로 정의
    
	public UserEntity addRole(Role role) {
		roles.add(role);
		return this;
	}
    
	public UserEntity addRoleByRange(String role) {
		for(int i=0; i<=Role.valueOf(role).ordinal(); i++) { //.ordinal() == 범위
			//addRole(Role.values()[i]);
			roles.add(Role.values()[i]); //addRole 메서드가 없는 경우
		}
		/*
		switch(role) {
		case "ADMIN":
			entity.addRole(Role.ADMIN);
		case "MGR":
			entity.addRole(Role.MGR);
		case "EMP":
			entity.addRole(Role.EMP);
		}
		 */
		return this;
	}
	
}