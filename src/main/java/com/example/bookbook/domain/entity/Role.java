
package com.example.bookbook.domain.entity;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum Role {
	
	User("구매자"),
	Seller("출판사"),
	ADMIN("관리자");
	
	private final String roleName;

	public final String roleName() { //getter 대신에 쓰는 메서드
		return roleName;
	}
}
