package com.example.bookbook.service.impl;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.bookbook.domain.dto.CombinedSellerDTO;
import com.example.bookbook.domain.entity.Role;
import com.example.bookbook.domain.entity.SellerEntity;
import com.example.bookbook.domain.entity.UserEntity;
import com.example.bookbook.domain.repository.SellerEntityRepository;
import com.example.bookbook.domain.repository.UserEntityRepository;
import com.example.bookbook.service.SellerService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SellerServiceProcess implements SellerService {
	
	private final UserEntityRepository userRepository;
    private final SellerEntityRepository sellerRepository;
    private final PasswordEncoder pe;

    @Override
    @Transactional
    public void signupProcess(CombinedSellerDTO dto) {
    	
        // 비밀번호 암호화
        String encodedPassword = pe.encode(dto.getPassword());
        dto.setPassword(encodedPassword);

        // UserEntity 생성 및 저장
        UserEntity userEntity = dto.toUserEntity();
        userEntity.addRole(Role.SELLER);
        userEntity = userRepository.save(userEntity);

        // SellerEntity 생성 및 저장 (User 정보 포함)
        SellerEntity sellerEntity = dto.toSellerEntity();
        sellerEntity = sellerRepository.save(sellerEntity);

        // UserEntity에 SellerEntity 연결
        userEntity.setSeller(sellerEntity);
        userRepository.save(userEntity);

    }
}