package com.project.bookbook.security;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import com.project.bookbook.domain.entity.ApprovalStatus;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class CustomAuthenticationFailureHandler extends SimpleUrlAuthenticationFailureHandler {

    @Override
    public void onAuthenticationFailure(HttpServletRequest request,
                                        HttpServletResponse response,
                                        AuthenticationException exception) throws IOException, ServletException {
    	 String errorMessage;
         if (exception instanceof BadCredentialsException) {
             errorMessage = "아이디 또는 비밀번호가 맞지 않습니다. 다시 확인해 주세요.";
         } else if (exception instanceof DisabledException) {
             String exceptionMessage = exception.getMessage();
             if ("delete".equals(exceptionMessage)) {
                 errorMessage = "탈퇴한 회원입니다.";
             } else if (exceptionMessage != null && exceptionMessage.startsWith("Seller account is:")) {
                 String statusString = exceptionMessage.split(":")[1].trim();
                 try {
                     ApprovalStatus status = ApprovalStatus.valueOf(statusString);
                     switch (status) {
                         case PENDING:
                             errorMessage = "승인 대기중입니다. 관리자의 승인을 기다려주세요.";
                             break;
                         case REJECTED:
                             errorMessage = "승인이 거절되었습니다. 관리자에게 문의하세요.";
                             break;
                         default:
                             errorMessage = "계정 상태를 확인할 수 없습니다. 관리자에게 문의하세요.";
                     }
                 } catch (IllegalArgumentException e) {
                     errorMessage = "계정 상태를 확인할 수 없습니다. 관리자에게 문의하세요.";
                 }
             } else {
                 errorMessage = "계정이 비활성화 되었습니다. 관리자에게 문의하세요.";
             }
         } else {
             errorMessage = "승인 대기중입니다. 관리자의 승인을 기다려주세요.";
         }

         errorMessage = URLEncoder.encode(errorMessage, StandardCharsets.UTF_8);
         
         // 요청 URL에 따라 다른 실패 URL 설정
         String requestURI = request.getRequestURI();
         String failureUrl;
         if (requestURI.contains("/login/admin")) {
             failureUrl = "/login/admin?error=true&exception=" + errorMessage;
         } else {
             failureUrl = "/login?error=true&exception=" + errorMessage;
         }

         setDefaultFailureUrl(failureUrl);
         super.onAuthenticationFailure(request, response, exception);
     }
 }