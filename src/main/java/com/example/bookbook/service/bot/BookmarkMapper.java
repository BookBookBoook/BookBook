package com.example.bookbook.service.bot;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.example.bookbook.domain.dto.bot.Bookmark;


@Mapper
public interface BookmarkMapper {

	List<Bookmark> findBookmarksByKeyword(String keyword);

}
