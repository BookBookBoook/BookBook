/*
 * package com.example.bookbook.service.impl;
 * 
 * import java.util.Arrays; import java.util.List;
 * 
 * import org.springframework.beans.factory.annotation.Value; import
 * org.springframework.stereotype.Service; import org.springframework.ui.Model;
 * import org.springframework.web.client.RestTemplate;
 * 
 * import com.example.bookbook.domain.dto.KakaoBookDto; import
 * com.example.bookbook.mapper.BookMapper; import
 * com.example.bookbook.service.BookService;
 * 
 * import lombok.RequiredArgsConstructor;
 * 
 * @Service
 * 
 * @RequiredArgsConstructor public class BookServiceProcess implements
 * BookService{
 * 
 * 
 * private final BookMapper bookMapper; private final KakaoApiServiceProcess
 * kakaoApiServiceProcess;
 * 
 * @Override public void getAllBooks(Model model) { List<KakaoBookDto> books =
 * bookMapper.selectAll(); model.addAttribute("books", books);
 * 
 * }
 * 
 * @Override public void searchBooks(String query,int page, int size, Model
 * model) { List<KakaoBookDto> searchResults =
 * kakaoApiServiceProcess.searchBooks(query, page, size);
 * model.addAttribute("books", searchResults); model.addAttribute("query",
 * query);
 * 
 * }
 * 
 * 
 * 
 * 
 * 
 * 
 * }
 */