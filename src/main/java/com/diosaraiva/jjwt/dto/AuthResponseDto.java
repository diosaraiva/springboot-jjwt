package com.diosaraiva.jjwt.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class AuthResponseDto {
	
	private String token;
	private String type;
	private String expiration;
	private String refreshToken;
}
