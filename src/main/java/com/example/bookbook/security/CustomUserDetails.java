package com.example.bookbook.security;

import java.util.stream.Collectors;

import org.springframework.security.core.userdetails.User; // 수정된 부분
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import com.example.bookbook.domain.entity.UserEntity;

import lombok.Getter;

@Getter
// principle 객체
public class CustomUserDetails extends User {

	 private static final long serialVersionUID = 1L;
	    private String email;
	    private String name;
	    private UserEntity userEntity; // UserEntity 참조 추가

	    public CustomUserDetails(UserEntity entity) {
	        super(entity.getEmail(), entity.getPassword(),
	                entity.getRoles().stream()
	                        .map(role -> new SimpleGrantedAuthority("ROLE_" + role.name()))
	                        .collect(Collectors.toSet()));
	        this.email = entity.getEmail();
	        this.name = entity.getUserName();
	        this.userEntity = entity; // UserEntity 저장
	    }

	    // UserEntity 반환 메소드 추가
	    public UserEntity getUser() {
	        return this.userEntity;
	    }
	}