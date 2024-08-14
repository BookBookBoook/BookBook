package com.example.bookbook.controller;

import java.io.IOException;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.multipart.MultipartFile;

import com.example.bookbook.domain.dto.CombinedSellerDTO;
import com.example.bookbook.domain.dto.ItemAndFileSaveDTO;
import com.example.bookbook.domain.dto.UserSaveDTO;
import com.example.bookbook.domain.entity.ImageEntity;
import com.example.bookbook.domain.entity.Role;
import com.example.bookbook.service.ImageService;
import com.example.bookbook.service.SellerService;
import com.example.bookbook.service.UserService;


import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
@SessionAttributes("combinedSellerDTO")
public class SignupController {
    
    private final UserService userService;
    private final SellerService sellerService;
    private final ImageService imageService;
    
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
        return "redirect:/login";
    }
    
    @PostMapping("/signup/save-and-redirect")
    @ResponseBody
    public ResponseEntity<String> saveAndRedirect(@ModelAttribute("combinedSellerDTO") CombinedSellerDTO dto) {
        try {
            // dto를 세션에 저장합니다. @SessionAttributes 어노테이션이 이를 처리합니다.
            // 필요한 경우 여기에 추가적인 로직을 구현할 수 있습니다.
            return ResponseEntity.ok("success");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body("error");
        }
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
    
    @PostMapping("/api/upload")
    @ResponseBody
    public ResponseEntity<Map<String, String>> uploadFile(@RequestParam("file") MultipartFile file, 
                                                          @ModelAttribute("combinedSellerDTO") CombinedSellerDTO dto) {
        try {
            if (file.isEmpty()) {
                return ResponseEntity.badRequest().body(Map.of("error", "File is empty"));
            }
            if (!file.getContentType().equals("image/png")) {
                return ResponseEntity.badRequest().body(Map.of("error", "Only PNG files are allowed"));
            }
            ImageEntity savedImage = imageService.uploadImage(file);
            dto.setBusinessRegImageId(savedImage.getId());
            return ResponseEntity.ok(Map.of("id", savedImage.getId().toString(), "url", savedImage.getFileUrl()));
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body(Map.of("error", "File upload failed: " + e.getMessage()));
        }
    }
}
