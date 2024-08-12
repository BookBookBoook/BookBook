package com.example.bookbook.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

import com.example.bookbook.domain.dto.CombinedSellerDTO;
import com.example.bookbook.domain.dto.UserSaveDTO;
import com.example.bookbook.domain.entity.Role;
import com.example.bookbook.service.SellerService;
import com.example.bookbook.service.UserService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
@SessionAttributes("combinedSellerDTO")
public class SignupController {
    
    private final UserService userService;
    private final SellerService sellerService;
    
    @ModelAttribute("combinedSellerDTO")
    public CombinedSellerDTO combinedSellerDTO() {
        return new CombinedSellerDTO();
    }

    @GetMapping("/signup")
    public String signup() {
        return "views/login/signup";
    }
    
    @PostMapping("/signup")
    public String signup(UserSaveDTO dto) {
        userService.signupProcess(dto, Role.USER);
        return "redirect:/";
    }
    
    @PostMapping("/signup/save-and-redirect")
    @ResponseBody
    public String saveAndRedirect(@ModelAttribute("combinedSellerDTO") CombinedSellerDTO dto) {
        // dto를 세션에 저장합니다. @SessionAttributes 어노테이션이 이를 처리합니다.
        return "success";
    }
    
    @GetMapping("/signup/admin")
    public String signupAdmin(@ModelAttribute("combinedSellerDTO") CombinedSellerDTO dto, Model model) {
        model.addAttribute("combinedSellerDTO", dto);
        return "views/login/signup-admin";
    }
    
    @PostMapping("/signup/admin")
    public String signupAdmin(@ModelAttribute("combinedSellerDTO") CombinedSellerDTO dto, SessionStatus status) {
        sellerService.signupProcess(dto);
        status.setComplete(); // 세션 정보 제거
        return "redirect:/";
    }
}