package com.project.bookbook.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.project.bookbook.domain.dto.FavoriteListDTO;

@Mapper
public interface FavoriteMapper {

	List<FavoriteListDTO> findByUser(long userId);

}
