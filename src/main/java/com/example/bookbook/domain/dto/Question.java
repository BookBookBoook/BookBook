package com.example.bookbook.domain.dto;
import lombok.Data;

@Data
public class Question {
	private long key;
	private String name;
	private String content;
}