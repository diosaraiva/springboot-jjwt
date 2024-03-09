package com.diosaraiva.jjwt.service.impl;

import java.security.Key;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import com.diosaraiva.jjwt.service.JwtService;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtServiceImpl implements JwtService {

	private static final Logger logger = LoggerFactory.getLogger(JwtServiceImpl.class);

	private String jwtSecret = "permasolutionsecretpermasolutionsecretpermasolutionsecretpermasolutionsecret";

	private int jwtExpirationMs = 604800;

	private Key key() {
		return Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtSecret));
	}

	private SignatureAlgorithm algorithm() {
		return SignatureAlgorithm.HS256;
	}

	public String generateJwtToken(Authentication authentication) {
		UserDetailsImpl userPrincipal = (UserDetailsImpl)authentication.getPrincipal();

		String token = Jwts.builder()
				.setSubject((userPrincipal.getUsername()))
				.setIssuedAt(new Date())
				.setExpiration(new Date((new Date()).getTime() + jwtExpirationMs))
				.signWith(key(), algorithm())
				.compact();

		logger.info("JwtService: Token generated sucessfully");

		return token;
	}

	public String getUsernameFromJwtToken(String token) {
		String username = Jwts.parser()
				.setSigningKey(key())
				.build()
				.parseClaimsJws(token)
				.getBody()
				.getSubject();

		return username;
	}

	public Date getExpirationFromJwtToken(String token) {
		Date expiration = Jwts.parser()
				.setSigningKey(key())
				.build()
				.parseClaimsJws(token)
				.getBody()
				.getExpiration();

		return expiration;
	}

	public Boolean validateJwtToken(String token) {
		try {
			Jwts.parser()
			.setSigningKey(key())
			.build()
			.parse(token);

			logger.info("JwtService: Token validated sucessfully");

			return true;
		} catch (MalformedJwtException e) {
			logger.error("JwtService: Invalid Token{}", e.getMessage());
		} catch (ExpiredJwtException e) {
			logger.error("JwtService: Token is expired: {}", e.getMessage());
		} catch (UnsupportedJwtException e) {
			logger.error("JwtService: Token is unsupported: {}", e.getMessage());
		} catch (IllegalArgumentException e) {
			//logger.error("JwtService claims string is empty: {}", e.getMessage());
		}

		return false;
	}
}
