package com.project.bookbook.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.project.bookbook.domain.dto.BookDTO;
import com.project.bookbook.domain.dto.BookHistoryDTO;
import com.project.bookbook.domain.dto.BookInfoDTO;
import com.project.bookbook.domain.dto.SearchQueryDTO;

@Mapper
public interface BookHistoryMapper {

	void save(BookHistoryDTO bookHistoryDTO);

	List<BookInfoDTO> findByUserId(@Param("userId") long userId);

	long findByUserIdAndBookNum(BookHistoryDTO bookHistoryDTO);

	void updateSearchDate(BookHistoryDTO bookHistoryDTO);
	
	void deleteAllByUserId(@Param("userId") long userId);

	List<String> findIsbn(@Param("userId") long userId);

	long findQueryAndUserId(Map<String, Object> params);

	void saveQuery(Map<String, Object> params);

	void updateQuery(Map<String, Object> params);

	List<SearchQueryDTO> findQueryByUserId(long userId);

}
