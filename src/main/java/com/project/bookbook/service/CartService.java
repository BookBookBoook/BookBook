package com.project.bookbook.service;


import com.project.bookbook.domain.entity.BookEntity;
import org.springframework.ui.Model;

import com.project.bookbook.security.CustomUserDetails;

public interface CartService {

	void findAllProcess(Model model, CustomUserDetails user);

	void deleteCartDetail(long cartDetailNum);


}