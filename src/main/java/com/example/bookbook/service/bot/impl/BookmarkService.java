package com.example.bookbook.service.bot.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.bookbook.domain.dto.bot.Bookmark;
import com.example.bookbook.service.bot.BookmarkMapper;

import java.util.List;

@Service
public class BookmarkService {

    @Autowired
    private BookmarkMapper bookmarkMapper;

    public List<Bookmark> getBookmarksByKeyword(String keyword) {
        return bookmarkMapper.findBookmarksByKeyword(keyword);
    }
}