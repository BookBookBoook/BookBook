package com.project.bookbook.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.project.bookbook.domain.dto.BookDTO;
import com.project.bookbook.domain.dto.BookInfoDTO;
import com.project.bookbook.domain.dto.SearchQueryDTO;
import com.project.bookbook.mapper.BookHistoryMapper;
import com.project.bookbook.security.CustomUserDetails;
import com.project.bookbook.service.HistoryService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class HistoryServiceProcess implements HistoryService{
	
	private final BookHistoryMapper historyMapper;
	
	@Override
	public void findAllProcess(CustomUserDetails user, Model model) {
		long userId = user.getUserId();
		List<BookInfoDTO> bookHistorys = historyMapper.findByUserId(userId);
		model.addAttribute("bookHistorys", bookHistorys);
		
		List<BookInfoDTO> limitedBookHistory = bookHistorys.stream().limit(3).collect(Collectors.toList());
		model.addAttribute("LimitedBookHistorys", limitedBookHistory);
		
	}

	@Override
	public void deleteProcess(CustomUserDetails user) {
		long userId = user.getUserId();
		historyMapper.deleteAllByUserId(userId);
	}

	@Override
	public void findQueryByUserId(CustomUserDetails user, Model model) {
		long userId = user.getUserId();
		List<SearchQueryDTO> searchHistorys = historyMapper.findQueryByUserId(userId);
		model.addAttribute("searchHistorys", searchHistorys);
		
	}

}
