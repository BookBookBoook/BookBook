package com.project.bookbook.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.project.bookbook.config.ReCaptchaConfig;
import com.project.bookbook.domain.dto.AdditionalUserInfoDTO;
import com.project.bookbook.domain.entity.UserEntity;
import com.project.bookbook.security.CustomUserDetails;
import com.project.bookbook.service.UserService;
import com.project.bookbook.service.impl.ReCaptchaService;

import jakarta.servlet.http.HttpServletRequest;

@Controller
@RequiredArgsConstructor
public class loginController {

    private final UserService userService;
    private final ReCaptchaService reCaptchaService;
    private final ReCaptchaConfig reCaptchaConfig;

    @GetMapping("/login")
    public String login(Model model) {
        model.addAttribute("reCaptchaSiteKey", reCaptchaConfig.getSiteKey());
        return "views/login/login";
    }

    @GetMapping("/login/admin")
    public String adminLogin(Model model) {
        model.addAttribute("reCaptchaSiteKey", reCaptchaConfig.getSiteKey());
        return "views/login/login-admin";
    }


    @PostMapping("/login")
    public String processLogin(@RequestParam("g-recaptcha-response") String recaptchaResponse,
                               HttpServletRequest request) {
        if (!reCaptchaService.verifyReCaptcha(recaptchaResponse)) {
            return "redirect:/login?error=recaptcha";
        }

        return "redirect:/login?continue";
    }

    @PostMapping("/login/admin")
    public String processAdminLogin(@RequestParam("g-recaptcha-response") String recaptchaResponse,
                                    HttpServletRequest request) {
        if (!reCaptchaService.verifyReCaptcha(recaptchaResponse)) {
            return "redirect:/login/admin?error=recaptcha";
        }
        return "redirect:/login/admin?continue";
    }

    @GetMapping("/admin/sign")
    public String adminSign() {
        return "views/login/admin-sign";
    }
    
    @GetMapping("/additional-info")
    public String showAdditionalInfoForm(@AuthenticationPrincipal CustomUserDetails userDetails, Model model) {
        model.addAttribute("user", userDetails.getUser());
        model.addAttribute("additionalInfo", new AdditionalUserInfoDTO());
        return "views/login/additional-info";
    }

    @PostMapping("/additional-info")
    public String processAdditionalInfo(@AuthenticationPrincipal CustomUserDetails userDetails, 
            AdditionalUserInfoDTO additionalInfo) {
        userService.updateAdditionalInfo(userDetails.getUser().getUserId(), additionalInfo);
        return "redirect:/"; // 정보 입력 후 홈페이지로 리다이렉트
    }
}