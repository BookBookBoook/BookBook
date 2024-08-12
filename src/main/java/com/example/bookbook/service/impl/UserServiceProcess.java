package com.example.bookbook.service.impl;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.bookbook.domain.dto.UserSaveDTO;
import com.example.bookbook.domain.entity.Role;
import com.example.bookbook.domain.entity.UserEntity;
import com.example.bookbook.domain.repository.UserEntityRepository;
import com.example.bookbook.service.UserService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserServiceProcess implements UserService{
	
	private final UserEntityRepository repository;
	private final PasswordEncoder pe;
	@Override
	public void signupProcess(UserSaveDTO dto, Role role) {
		UserEntity userEntity = dto.toEntity(pe);
        userEntity.addRole(role);
        repository.save(userEntity);
    }
}