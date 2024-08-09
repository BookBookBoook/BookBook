package com.example.bookbook.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface ExamMapper {
	@Select("SELECT COUNT(*) FROM users")
    int countUsers();
}
