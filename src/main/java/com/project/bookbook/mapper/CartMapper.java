package com.project.bookbook.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.project.bookbook.domain.dto.CartBookCntDTO;
import com.project.bookbook.domain.dto.CartDetailDTO;

@Mapper
public interface CartMapper {

	List<CartDetailDTO> findAllCartDetail(long userId);

	void deleteCartDetail(long cartDetailNum);

	List<CartBookCntDTO> selectCartDetailsByIds(List<Long> cartDetailNums);

}
