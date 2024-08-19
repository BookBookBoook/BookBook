package com.project.bookbook.controller;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReviewCreateRequest {
	private String content;
    private int rate;
}