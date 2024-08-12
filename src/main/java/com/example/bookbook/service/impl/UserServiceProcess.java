package com.example.bookbook.service.impl;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.bookbook.domain.dto.AdditionalUserInfoDTO;
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

	@Override
	public void updateAdditionalInfo(long userId, AdditionalUserInfoDTO additionalInfo) {
		UserEntity user = repository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        
        user.setUserRRN(additionalInfo.getUserRRN());
        user.setGender(additionalInfo.getGender());
        user.setPhoneNumber(additionalInfo.getPhoneNumber());
        user.setBirthDate(additionalInfo.getBirthDate());
        user.setPostcode(additionalInfo.getPostcode());
        user.setAddress(additionalInfo.getAddress());
        user.setExtraAddress(additionalInfo.getExtraAddress());
        user.setDetailAddress(additionalInfo.getDetailAddress());

        repository.save(user);
    }
}