package com.project.bookbook.controller;

import java.io.IOException;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.project.bookbook.domain.dto.RequestRegistDTO;
import com.project.bookbook.service.RegistService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
public class SellerController {

    private final RegistService registService;
    
    @GetMapping("/seller")
    public String seller() {
        return "views/seller/index";
    }

    @GetMapping("/seller/inventory")
    public String sellerInventory() {
        return "views/seller/inventory";
    }

    @GetMapping("/seller/inventory/write")
    public String sellerInventoryWrite() {
        return "views/seller/inventory-write";
    }

    @PostMapping("/seller/inventory/fileupload")
    @ResponseBody
    public Map<String, String> imgUpload(@RequestParam(value = "bookImgInput", required = false) MultipartFile bookImg) throws IOException {
    	
    	System.out.println(">>>???");
        if (bookImg == null || bookImg.isEmpty()) {
            return Map.of("error", "파일이 첨부되지 않았습니다.");
        }

        return registService.uploadFileToS3(bookImg);
    }


    @PostMapping("/seller/inventory/request")
    public String requestRegist(RequestRegistDTO requestRegistDTO) {
       
    	registService.saveProcess(requestRegistDTO);
       
        return "redirect:/seller";
    }


    @GetMapping("/seller/order/exchange")
    public String sellerExchange() {
        return "views/seller/exchange";
    }

    @GetMapping("/seller/order")
    public String sellerOrder() {
        return "views/seller/order";
    }

    @GetMapping("/seller/order/refund")
    public String sellerRefund() {
        return "views/seller/refund";
    }

    @GetMapping("/seller/mypage")
    public String sellerMypage() {
        return "views/seller/mypage";
    }

    @GetMapping("/seller/review")
    public String sellerReview() {
        return "views/seller/review";
    }

    @GetMapping("/seller/user")
    public String sellerUser() {
        return "views/seller/users";
    }

    @GetMapping("/seller/user/detail")
    public String sellerDetail() {
        return "views/seller/users-detail";
    }
}
