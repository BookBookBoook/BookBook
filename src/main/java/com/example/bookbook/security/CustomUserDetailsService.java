package com.example.bookbook.security;

import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.bookbook.domain.entity.SellerEntity;
import com.example.bookbook.domain.entity.UserEntity;
import com.example.bookbook.domain.repository.SellerEntityRepository;
import com.example.bookbook.domain.repository.UserEntityRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService{
	

    private final UserEntityRepository userRepository;
    private final SellerEntityRepository sellerRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity user = userRepository.findByEmail(username)
                .orElseGet(() -> sellerRepository.findByBusinessNum(username)
                        .map(SellerEntity::getUser)
                        .orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + username)));
        
        //status가 0이 아니라면 탈퇴처리된 회원
        if(user.getStatus() != 0) {
        	throw new DisabledException("delete");
        }
        
        return new CustomUserDetails(user);
    }
}