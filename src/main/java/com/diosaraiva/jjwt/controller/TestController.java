package com.diosaraiva.jjwt.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.diosaraiva.jjwt.entity.Roles;
import com.diosaraiva.jjwt.service.RoleService;

@RestController
@RequestMapping("/auth/test")
public class TestController {

	@Autowired
	RoleService roleService;

	@GetMapping("/setup")
	public String setupEnv() {

		Roles admRole = Roles.builder()
				.role("ROLE_ADMIN")
				.build();

		Roles modRole = Roles.builder()
				.role("ROLE_MODERATOR")
				.build();

		Roles usrRole = Roles.builder()
				.role("ROLE_USER")
				.build();

		roleService.addRole(admRole);
		roleService.addRole(modRole);
		roleService.addRole(usrRole);

		return "Roles added.";
	}

	@GetMapping("/all")
	public String allAccess() {
		return "Public Content.";
	}

	@GetMapping("/user")
	@PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
	public String userAccess() {
		return "User Content.";
	}

	@GetMapping("/mod")
	@PreAuthorize("hasRole('MODERATOR')")
	public String moderatorAccess() {
		return "Moderator Board.";
	}

	@GetMapping("/admin")
	@PreAuthorize("hasRole('ADMIN')")
	public String adminAccess() {
		return "Admin Board.";
	}
}
