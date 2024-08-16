package com.project.bookbook.service.api;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.fasterxml.jackson.core.type.TypeReference;
import com.project.bookbook.domain.api.OpenApiUtil;
import com.project.bookbook.domain.dto.api.DriveFileListResponse;
import com.project.bookbook.domain.dto.api.DriveFileListResponseDTO;
import com.project.bookbook.domain.dto.api.File;
import com.project.bookbook.domain.dto.api.Files;
import com.project.bookbook.domain.dto.api.NaverTokenDTO;

import lombok.RequiredArgsConstructor;


@Service
@RequiredArgsConstructor
public class DriveService {
	
	private final OpenApiUtil openApiUtil;
	
	@Value("${naver.client.id}")
	private String clientId;
	
	@Value("${naver.client.secret}")
	private String clientSecret;
	
	@Value("${naver.client.domain}")
	private String domainId;
	
	public String getAccessTokenForCode(String code) throws Exception {
        NaverTokenDTO tokenResult = getAccessToken(code);
        return tokenResult.getAccess_token();
    }
	
	public List<Files> rootfileRead(String accessToken, Model model) throws Exception {
        System.out.println("Access Token: " + accessToken);

        String apiUrl = "https://www.worksapis.com/v1.0/users/me/drive/files";

        Map<String, String> headers = new HashMap<>();
        headers.put("Authorization", "Bearer " + accessToken);

        String fileResponseResult = openApiUtil.request(apiUrl, headers, "GET", null);
        System.out.println("fileResponseResult: " + fileResponseResult);
        
        DriveFileListResponse resultFiles = openApiUtil.objectMapper(fileResponseResult, new TypeReference<DriveFileListResponse>() {});
        List<Files> rootfiles = resultFiles.getFiles();

        model.addAttribute("rootfiles", rootfiles);
        model.addAttribute("metadata", resultFiles.getResponseMetaData());

        return rootfiles;
    }
	
    public List<File> fileRead(String accessToken, String fileId, Model model) throws Exception {
        System.out.println(accessToken);

        String apiUrl = "https://www.worksapis.com/v1.0/users/me/drive/files/" + fileId + "/children";

        Map<String, String> headers = new HashMap<>();
        headers.put("Authorization", "Bearer " + accessToken);

        String fileResponseResult = openApiUtil.request(apiUrl, headers, "GET", null);
        System.out.println("fileResponseResult: " + fileResponseResult);
        
        DriveFileListResponseDTO resultFiles = openApiUtil.objectMapper(fileResponseResult, new TypeReference<DriveFileListResponseDTO>() {});
        List<File> files = resultFiles.getFiles();

        model.addAttribute("files", files);
        model.addAttribute("metadata", resultFiles.getResponseMetadata());

        return files;
    }

	
    public File getFileDetails(String accessToken, String fileId) throws Exception {
        System.out.println("Fetching file details for fileId: " + fileId);

        String apiUrl = "https://www.worksapis.com/v1.0/users/me/drive/files/" + fileId;

        Map<String, String> headers = new HashMap<>();
        headers.put("Authorization", "Bearer " + accessToken);

        String fileResponseResult = openApiUtil.request(apiUrl, headers, "GET", null);
        System.out.println("File details response: " + fileResponseResult);
        
        File file = openApiUtil.objectMapper(fileResponseResult, new TypeReference<File>() {});
        
        // 이미지 파일인 경우 URL 추가
        if (file.getFileType().toString().startsWith("image")) {
            file.setImageUrl(apiUrl + "/content"); // 이미지 파일의 콘텐츠 URL 설정
        }

        return file;
    }



    private NaverTokenDTO getAccessToken(String code) throws Exception {
        String apiUrl = "https://auth.worksmobile.com/oauth2/v2.0/token";

        Map<String, String> params = new HashMap<>();
        params.put("code", code);
        params.put("grant_type", "authorization_code");
        params.put("client_id", clientId);
        params.put("client_secret", clientSecret);

        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/x-www-form-urlencoded");

        String responseBody = openApiUtil.request(apiUrl, headers, "POST", encodeParameters(params));
        return openApiUtil.objectMapper(responseBody, new TypeReference<NaverTokenDTO>() {});
    }
    
    
    
    
    private String encodeParameters(Map<String, String> params) {
        StringBuilder result = new StringBuilder();
        for (Map.Entry<String, String> entry : params.entrySet()) {
            result.append(entry.getKey());
            result.append("=");
            result.append(entry.getValue());
            result.append("&");
        }
        return result.toString();
    }
}