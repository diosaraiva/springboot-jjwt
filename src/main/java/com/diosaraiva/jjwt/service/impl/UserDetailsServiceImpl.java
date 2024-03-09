package com.diosaraiva.jjwt.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.diosaraiva.jjwt.entity.AppUser;
import com.diosaraiva.jjwt.repository.AppUserRepository;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

	@Autowired
	AppUserRepository appUserRepository;

	@Override
	@Transactional
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		AppUser appUser = appUserRepository.findByUsername(username)
				.orElseThrow(() -> new UsernameNotFoundException("User Not Found with username: " + username));

		List<GrantedAuthority> authorities = appUser.getRoles().stream()
				.map(role -> new SimpleGrantedAuthority(role.getRole()))
				.collect(Collectors.toList());

		UserDetails userDetails = UserDetailsImpl.builder()
				.id(appUser.getId())
				.username(appUser.getUsername())
				.email(appUser.getEmail())
				.password(appUser.getPassword())
				.authorities(authorities)
				.build();
		
		return userDetails;
	}
}
