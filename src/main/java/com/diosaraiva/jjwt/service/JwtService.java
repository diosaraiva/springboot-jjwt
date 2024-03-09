package com.diosaraiva.jjwt.service;

import java.util.Date;

import org.springframework.security.core.Authentication;

public interface JwtService {

	String generateJwtToken(Authentication authentication);
	
	String getUsernameFromJwtToken(String token);
	
	Date getExpirationFromJwtToken(String token);
	
	Boolean validateJwtToken(String token);
}
