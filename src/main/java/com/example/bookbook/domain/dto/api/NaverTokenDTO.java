package com.example.bookbook.domain.dto.api;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class NaverTokenDTO {
	
	private String access_token;
	private String refresh_token;
	private String expires_in;
	private String scope;
	private String token_type;
	
}
