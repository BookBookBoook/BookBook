package com.project.bookbook.service;

import org.springframework.ui.Model;

import com.project.bookbook.domain.dto.mypage.LibraryApiResponseDTO;
import com.project.bookbook.domain.dto.mypage.selectRecommendDTO;
import com.project.bookbook.security.CustomUserDetails;

public interface RecommendService {

	void recommendBook(long merchantUid, Model model);

	void userRecommend(CustomUserDetails user, Model model);

	LibraryApiResponseDTO userSelectRecommend(selectRecommendDTO dto);

}
