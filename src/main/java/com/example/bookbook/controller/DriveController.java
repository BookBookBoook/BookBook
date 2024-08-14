/*
 * package com.example.bookbook.controller;
 * 
 * import org.springframework.stereotype.Controller; import
 * org.springframework.ui.Model; import
 * org.springframework.web.bind.annotation.GetMapping; import
 * org.springframework.web.bind.annotation.RequestParam;
 * 
 * import com.example.bookbook.service.DriveService;
 * 
 * import lombok.RequiredArgsConstructor;
 * 
 * @Controller
 * 
 * @RequiredArgsConstructor public class DriveController {
 * 
 * private final DriveService service;
 * 
 * @GetMapping("/oauth2/code") public String redirectUri(@RequestParam("code")
 * String code, @RequestParam("state") String state, Model model) throws
 * Exception {
 * 
 * if(state.equals("orgunit.read")) {
 * 
 * }else if(state.equals("orgunit")) {
 * 
 * } return "redirect:/"; }
 * 
 * }
 */