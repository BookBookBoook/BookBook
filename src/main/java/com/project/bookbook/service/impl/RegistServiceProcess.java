package com.project.bookbook.service.impl;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.project.bookbook.domain.dto.RequestRegistDTO;
import com.project.bookbook.domain.entity.BookEntity;
import com.project.bookbook.domain.repository.BookRepository;
import com.project.bookbook.service.RegistService;
import com.project.bookbook.utils.FileUploadUtil;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RegistServiceProcess implements RegistService{
	
	private final BookRepository bookRepository;
	private final FileUploadUtil fileUploadUtil;
	
	@Override
	public Map<String, String> uploadFileToS3(MultipartFile bookImg) throws IOException {
		return fileUploadUtil.uploadFileToS3(bookImg);
	}

	@Override
	public void saveProcess(RequestRegistDTO requestRegistDTO) {
		//이미지는 temp-> upload	
		List<String> uploadKeys = fileUploadUtil.ImagesToS3(requestRegistDTO.getBucketkey());
		//상품정보 먼저 저장하세요
		//DB저장하세요-파일은 여러개 파일 테이블
		BookEntity book = requestRegistDTO.toRequest();
		bookRepository.save(book);
		
	}

}
