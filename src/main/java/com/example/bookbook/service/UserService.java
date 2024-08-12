package com.example.bookbook.service;

import com.example.bookbook.domain.dto.UserSaveDTO;
import com.example.bookbook.domain.entity.Role;

public interface UserService {

	void signupProcess(UserSaveDTO dto, Role role);

	

}