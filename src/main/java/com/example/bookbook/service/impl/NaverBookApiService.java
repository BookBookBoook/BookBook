package com.example.bookbook.service.impl;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.StringReader;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.xml.sax.InputSource;

import com.example.bookbook.domain.entity.Book;
import com.example.bookbook.domain.entity.BookEntity;

import net.minidev.json.JSONObject;

@Service
public class NaverBookApiService {

    @Value("${naver.openapi.clientId}")
    private String clientId;

    @Value("${naver.openapi.clientSecret}")
    private String clientSecret;

    private static final String SEARCH_URL = "https://openapi.naver.com/v1/search/book.json";
    private static final String DETAIL_URL = "https://openapi.naver.com/v1/search/book_adv.xml";


}
    

