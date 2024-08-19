package com.project.bookbook.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.project.bookbook.domain.dto.CombinedSellerDTO;
import com.project.bookbook.domain.entity.ApprovalStatus;
import com.project.bookbook.domain.entity.ImageEntity;
import com.project.bookbook.domain.entity.Role;
import com.project.bookbook.domain.entity.SellerEntity;
import com.project.bookbook.domain.entity.UserEntity;
import com.project.bookbook.domain.repository.SellerEntityRepository;
import com.project.bookbook.domain.repository.UserEntityRepository;
import com.project.bookbook.service.ImageService;
import com.project.bookbook.service.SellerService;
import com.project.bookbook.utils.FileUploadUtil;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SellerServiceProcess implements SellerService {
	
	private final UserEntityRepository userRepository;
    private final SellerEntityRepository sellerRepository;
    private final PasswordEncoder pe;
    private final ImageService imageService;

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
        sellerEntity.setApprovalStatus(ApprovalStatus.PENDING);

        // UserEntity에 SellerEntity 연결
        userEntity.setSeller(sellerEntity);
        userRepository.save(userEntity);
        
        // 사업자 등록증 이미지 처리
        if (dto.getBusinessRegImageId() != null) {
            ImageEntity image = imageService.getImageById(dto.getBusinessRegImageId());
            sellerEntity.setBusinessRegImage(image);
            sellerEntity.setBusinessReg(image.getFileUrl());
        }

    }


	@Override
	public List<SellerEntity> getPendingSellers() {
		return sellerRepository.findByApprovalStatus(ApprovalStatus.PENDING);
	}

	@Override
	public void approveSeller(Long id) {
		 SellerEntity seller = sellerRepository.findById(id)
		            .orElseThrow(() -> new RuntimeException("Seller not found"));
		        seller.setApprovalStatus(ApprovalStatus.APPROVED);
		        sellerRepository.save(seller);
		    }

	@Override
	public void rejectSeller(Long id) {
		SellerEntity seller = sellerRepository.findById(id)
	            .orElseThrow(() -> new RuntimeException("Seller not found"));
	        seller.setApprovalStatus(ApprovalStatus.REJECTED);
	        sellerRepository.save(seller);
	    }
	
	
}