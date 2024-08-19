package com.project.bookbook.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.project.bookbook.domain.dto.InventoryDTO;

@Mapper
public interface InventoryMapper {

	@Select("select * from book order by book_num desc")
	List<InventoryDTO> findBook();
}
