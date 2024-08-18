package com.project.bookbook.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.project.bookbook.domain.dto.OrderDetailDTO;

@Mapper
public interface OrdersMapper {

	void insertUserOrder(Map<String, Long> params);

	void insertUserOrdersDetail(Map<String, Object> cartDetailParams);

	List<OrderDetailDTO> findByOrderBook(long merchantUid);

}
