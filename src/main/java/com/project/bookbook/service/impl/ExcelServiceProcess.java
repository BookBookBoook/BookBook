package com.project.bookbook.service.impl;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

import org.springframework.stereotype.Service;

import com.project.bookbook.domain.dto.InventoryDTO;
import com.project.bookbook.mapper.InventoryMapper;
import com.project.bookbook.service.ExcelService;

import lombok.RequiredArgsConstructor;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

@Service
@RequiredArgsConstructor
public class ExcelServiceProcess implements ExcelService {

	private final InventoryMapper inventoryMapper;

	public ByteArrayInputStream generateExcel() {
		try (Workbook workbook = new XSSFWorkbook()) {
			Sheet sheet = workbook.createSheet("상품목록");

			// 헤더 행 생성
			Row headerRow = sheet.createRow(0);
			headerRow.createCell(0).setCellValue("도서번호");
			headerRow.createCell(1).setCellValue("제목");
			headerRow.createCell(2).setCellValue("저자");
			headerRow.createCell(3).setCellValue("출판사");
			headerRow.createCell(4).setCellValue("출간일자");
			headerRow.createCell(5).setCellValue("판매가");
			headerRow.createCell(6).setCellValue("재고");

			// 실제 데이터 행 추가
			List<InventoryDTO> inventoryList = fetchInventoryData(); // 데이터 가져오는 메서드 (예시)
			int rowNum = 1;
			for (InventoryDTO item : inventoryList) {
				Row row = sheet.createRow(rowNum++);
				row.createCell(0).setCellValue(item.getBookNum());
				row.createCell(1).setCellValue(item.getBookName());
				row.createCell(2).setCellValue(item.getAuthor());
				row.createCell(3).setCellValue(item.getPublisher());
				row.createCell(4).setCellValue(item.getPubdate().toString());
				row.createCell(5).setCellValue(item.getDiscount());
				row.createCell(6).setCellValue(item.getStock());
			}

			// 데이터를 ByteArrayOutputStream에 쓰고 이를 ByteArrayInputStream으로 변환
			try (ByteArrayOutputStream out = new ByteArrayOutputStream()) {
				workbook.write(out);
				return new ByteArrayInputStream(out.toByteArray());
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	private List<InventoryDTO> fetchInventoryData() {
		return inventoryMapper.getInventoryData();
	}
}
