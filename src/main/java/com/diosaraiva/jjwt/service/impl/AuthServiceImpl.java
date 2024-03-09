package com.diosaraiva.jjwt.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.diosaraiva.jjwt.entity.AppUser;
import com.diosaraiva.jjwt.service.AppUserService;
import com.diosaraiva.jjwt.service.AuthService;
import com.diosaraiva.jjwt.service.JwtService;
import com.diosaraiva.jjwt.service.RoleService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

	@Autowired
	AuthenticationManager authenticationManager;

	@Autowired
	JwtService jwtService;

	@Autowired
	AppUserService appUserService;

	@Autowired
	RoleService roleService;

	public UserDetailsImpl authenticateUser(String username, String password) throws Exception
	{
		username = appUserService.validateUsername(username);

		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(username, password));

		SecurityContextHolder.getContext().setAuthentication(authentication);

		UserDetailsImpl userDetails = (UserDetailsImpl)authentication.getPrincipal();

		userDetails.setToken(jwtService.generateJwtToken(authentication));

		userDetails.setExpiration(jwtService.getExpirationFromJwtToken(userDetails.getToken()));

		return userDetails;
	}

	public AppUser registerUser(String username, String email, String password)
	{
		AppUser appUser = AppUser.builder()
				.username(username)
				.email(email)
				.password(password)
				.build();

		appUser = appUserService.addAppUser(appUser);

		return appUser;
	}

	public AppUser registerUser(AppUser appUser)
	{
		return appUserService.addAppUser(appUser);
	}
}
