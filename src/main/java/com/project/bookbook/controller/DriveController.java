package com.project.bookbook.controller;


import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller; import
org.springframework.ui.Model; import
org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import
org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.project.bookbook.domain.dto.api.File;
import com.project.bookbook.domain.dto.api.Files;
import com.project.bookbook.security.CustomUserDetails;
import com.project.bookbook.service.api.DriveService;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class DriveController {
	  
	    private final DriveService driveservice;
	    

		@Value("${naver.client.id}")
		private String clientId;
		
		@Value("${naver.client.secret}")
		private String clientSecret;
		
		@Value("${naver.client.scope}")
		private String clientScope;
		
		@Value("${naver.client.redirect-uri}")
		private String redirectUri;

		@GetMapping("/oauth2/code")
	    public String redirectUri(@RequestParam("code") String code,
	                              @RequestParam("state") String state,
	                              Model model,
	                              HttpSession session) throws Exception {
	        if (state.equals("drive_access")) {
	            String accessToken = driveservice.getAccessTokenForCode(code);
	            session.setAttribute("accessToken", accessToken);
	            // 추가된 로그
	            System.out.println("AccessToken stored in session: " + accessToken);
	            List<Files> rootfiles = driveservice.rootfileRead(accessToken, model);
	            model.addAttribute("rootfiles", rootfiles);
	            return "views/naver/root-drive";
	        }
	        return "redirect:/";
	    }
	    
	    @GetMapping("/drive")
	    public String rootfile(Model model) throws Exception {
	        return "views/naver/drive-sub";
	    }
	    
	    @GetMapping("/drive/files")
	    public String listFiles(@RequestParam("fileId") String fileId,
	                            Model model,
	                            HttpSession session) throws Exception {
	        String accessToken = (String) session.getAttribute("accessToken");
	        System.out.println("AccessToken retrieved from session: " + accessToken);
	        if (accessToken == null) {
	            return "redirect:/drive/auth";
	        }
	        List<File> fileList = driveservice.fileRead(accessToken, fileId, model);
	        
	        // 디버깅을 위한 로그 추가
	        System.out.println("File List:");
	        for (File file : fileList) {
	            System.out.println(file);
	        }
	        
	        model.addAttribute("files", fileList);
	        model.addAttribute("parentFileId", fileId);
	        return "views/naver/drive";
	    }


	    @PostMapping("/drive/files")
	    public String listFilesPost(@RequestParam("fileId") String fileId,
	                                Model model,
	                                HttpSession session) throws Exception {
	        // 세션에서 액세스 토큰을 가져오는 부분 추가
	        String accessToken = (String) session.getAttribute("accessToken");
	        // 추가된 로그
	        System.out.println("AccessToken retrieved from session (POST): " + accessToken);
	        if (accessToken == null) {
	            return "redirect:/drive/auth";  // 토큰이 없으면 인증 페이지로 리다이렉트
	        }
	        List<File> fileList = driveservice.fileRead(accessToken, fileId, model);
	        model.addAttribute("files", fileList);
	        model.addAttribute("parentFileId", fileId);
	        return "views/naver/drive";
	    }

	    @GetMapping("/drive/auth")
	    public String driveAuth(Model model) {
	        model.addAttribute("clientId", clientId);
	        model.addAttribute("redirectUri", redirectUri);
	        model.addAttribute("scope", clientScope);
	        return "views/naver/drive-sub";
	    }
	    
	    @GetMapping("/drive/file-details")
	    @ResponseBody
	    public ResponseEntity<File> getFileDetails(@RequestParam("fileId") String fileId,
	                                               HttpSession session) {
	        String accessToken = (String) session.getAttribute("accessToken");
	        System.out.println("AccessToken retrieved from session for file details: " + accessToken);
	        if (accessToken == null) {
	            return ResponseEntity.badRequest().build();
	        }
	        try {
	            File fileDetails = driveservice.getFileDetails(accessToken, fileId);
	            return ResponseEntity.ok(fileDetails);
	        } catch (Exception e) {
	            System.err.println("Error fetching file details: " + e.getMessage());
	            return ResponseEntity.internalServerError().build();
	        }
	    }
	    
		@PostMapping("/userinfo")
		@ResponseBody
		public Map<String, Object> handleUserInfo(@RequestBody Map<String, Object> userInfo) {
	        // 사용자 정보를 처리하는 로직 (예: 사용자 세션 생성, 데이터베이스 저장 등)
	        System.out.println("Received user info: " + userInfo);
	        
	        // 사용자 정보 처리 후 응답 반환
	        return Map.of("status", "success", "message", "User info processed successfully");
	    }
	}