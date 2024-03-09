package com.diosaraiva.jjwt.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.diosaraiva.jjwt.dto.AppUserDto;
import com.diosaraiva.jjwt.dto.AuthRequestDto;
import com.diosaraiva.jjwt.dto.AuthResponseDto;
import com.diosaraiva.jjwt.dto.SignupRequestDto;
import com.diosaraiva.jjwt.entity.AppUser;
import com.diosaraiva.jjwt.service.AppUserService;
import com.diosaraiva.jjwt.service.AuthService;
import com.diosaraiva.jjwt.service.impl.UserDetailsImpl;

@RestController
@RequestMapping("/auth")
public class AuthController {

	@Autowired
	AuthService authService;

	@Autowired
	AppUserService appUserService;

	@PostMapping("/signup")
	public ResponseEntity<?> registerCredentials(@RequestBody SignupRequestDto signUpRequestDto) {
		try {
			Optional<AppUser> appUser = 
					Optional.ofNullable(authService.registerUser(
							signUpRequestDto.getUsername(),
							signUpRequestDto.getEmail(), 
							signUpRequestDto.getPassword()));

			if (appUser.isPresent()) {
				AppUserDto appUserDto = AppUserDto.builder()
						.id(appUser.get().getId())
						
						.build();

				String message = 
						"User registered sucessfully: "
								+ appUserDto.getUsername()
								+ " || "
								+ appUserDto.getEmail().substring(0, 5)
								+ "***************";

				return new ResponseEntity<>(message, HttpStatus.CREATED);
			}
		} catch (Exception e) {
			return new ResponseEntity<>("Erro: " + e.getMessage(), HttpStatus.NOT_ACCEPTABLE);
		}

		return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@PostMapping("/signupfull")
	public ResponseEntity<?> registerAppUser(@RequestBody AppUserDto appUserDto) {

		try {
			Optional<AppUser> appUser = 
					Optional.ofNullable(appUserService.addAppUser(
							AppUser.builder()
							.id(appUserDto.getId())
							
							.build()));

			if (appUser.isPresent()) {
				AppUserDto returnedAppUserDto = AppUserDto.builder()
						.id(appUser.get().getId())
						
						.build();

				String message = 
						"User registered sucessfully: "
								+ returnedAppUserDto.getUsername()
								+ " || "
								+ returnedAppUserDto.getEmail().substring(0, 5)
								+ "***************";

				return new ResponseEntity<>(message, HttpStatus.CREATED);
			} else {
				return new ResponseEntity<>(appUserDto, HttpStatus.INTERNAL_SERVER_ERROR);
			}
		} catch (Exception e) {
			return new ResponseEntity<>(appUserDto, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PostMapping("/signin")
	public ResponseEntity<?> authenticateUser(@RequestBody AuthRequestDto authRequestDto) {
		try {
			Optional<UserDetailsImpl> userDetails = 
					Optional.ofNullable(authService.authenticateUser(
							authRequestDto.getUsername(),
							authRequestDto.getPassword()));

			if (userDetails.isPresent()) {
				AuthResponseDto authResponseDto = AuthResponseDto.builder()
						.token(userDetails.get().getToken())
						.type("Bearer")
						.expiration(userDetails.get().getExpiration().toString())
						.build();

				return new ResponseEntity<>(authResponseDto, HttpStatus.OK);
			}		
		} catch (Exception e) {
			return new ResponseEntity<>("Erro: " + e.getMessage(), HttpStatus.BAD_REQUEST);
		}

		return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
