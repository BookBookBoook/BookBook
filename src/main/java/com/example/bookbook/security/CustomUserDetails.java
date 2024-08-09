package com.example.bookbook.security;

import java.util.stream.Collectors;

import org.springframework.security.core.userdetails.User; // 수정된 부분
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import com.example.bookbook.domain.entity.UserEntity;

import lombok.Getter;

@Getter
// principle 객체
public class CustomUserDetails extends User {

    private static final long serialVersionUID = 1L; // 1L : 시리얼 정보

    private String email; // = username
    private String name; // 한글이름

    public CustomUserDetails(UserEntity entity) {
        super(entity.getEmail(), entity.getPassword(),
                entity.getRoles().stream()
                .map(role -> new SimpleGrantedAuthority("ROLE_" + role))
                .collect(Collectors.toSet()));
        email = entity.getEmail();
        name = entity.getUserName(); // 사용자 이름을 가져오는 것으로 수정
    }
}
