package com.example.bookbook.service.impl;

import java.util.List;

import com.example.bookbook.domain.dto.BookDTO;

public class KakaoApiResponse {
    private List<BookDTO> documents;

    public List<BookDTO> getDocuments() {
        return documents;
    }

    public void setDocuments(List<BookDTO> documents) {
        this.documents = documents;
    }
}
