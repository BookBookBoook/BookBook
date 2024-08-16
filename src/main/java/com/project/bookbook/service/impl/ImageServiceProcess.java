package com.project.bookbook.service.impl;

import java.io.IOException;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.project.bookbook.domain.entity.ImageEntity;
import com.project.bookbook.domain.repository.ImageEntityRepository;
import com.project.bookbook.service.ImageService;
import com.project.bookbook.utils.FileUploadUtil;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ImageServiceProcess implements ImageService {
	
	private final ImageEntityRepository imageRepository;
    private final FileUploadUtil fileUploadUtil;

	
	
	
	@Override
	@Transactional
	public ImageEntity uploadImage(MultipartFile file) throws IOException {
		  Map<String, String> uploadResult = fileUploadUtil.uploadFileToS3(file);
	        
	        ImageEntity image = new ImageEntity();
	        image.setFileName(uploadResult.get("fileName"));
	        image.setFileUrl(uploadResult.get("url"));
	        image.setFileType(uploadResult.get("fileType"));
	        image.setFileSize(Long.parseLong(uploadResult.get("fileSize")));

	        return imageRepository.save(image);
	    }


	@Override
	public ImageEntity getImageById(Long id) {
		 return imageRepository.findById(id)
	                .orElseThrow(() -> new RuntimeException("Image not found"));
	    }

	@Override
	@Transactional
	public void deleteImage(Long id) {
		ImageEntity image = getImageById(id);
        imageRepository.delete(image);
    }
}