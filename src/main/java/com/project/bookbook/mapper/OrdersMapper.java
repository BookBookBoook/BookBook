package com.project.bookbook.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.project.bookbook.domain.dto.OrderDetailDTO;
import com.project.bookbook.domain.dto.OrdersDetailDTO;
import com.project.bookbook.domain.dto.PaymentPostDTO;

@Mapper
public interface OrdersMapper {

	void insertUserOrder(Map<String, Long> params);

	void insertUserOrdersDetail(Map<String, Object> cartDetailParams);

	List<OrderDetailDTO> findByOrderBook(long merchantUid);

	void orderCompletion(PaymentPostDTO dto);

	OrdersDetailDTO findByMerchantUid(long merchantUid);

}
