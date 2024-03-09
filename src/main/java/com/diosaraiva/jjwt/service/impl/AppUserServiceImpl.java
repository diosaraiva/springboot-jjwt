package com.diosaraiva.jjwt.service.impl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.regex.Pattern;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.diosaraiva.jjwt.entity.AppUser;
import com.diosaraiva.jjwt.entity.Roles;
import com.diosaraiva.jjwt.repository.AppUserRepository;
import com.diosaraiva.jjwt.service.AppUserService;
import com.diosaraiva.jjwt.service.RoleService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AppUserServiceImpl implements AppUserService
{
	private static Logger logger = LogManager.getLogger(AppUserServiceImpl.class);

	@Autowired
	RoleService roleService;

	@Autowired
	AppUserRepository appUserRepository;

	@Autowired
	BCryptPasswordEncoder encoder;

	//Create
	@Override
	public AppUser addAppUser(AppUser appUser)
	{
		try {			
			String validatedUsername = validateUsername(appUser.getUsername());
			if(existsByUsername(validatedUsername) && getIdByUsername(validatedUsername) != appUser.getId()) {
				throw new Exception("AppUser: Username already registered");
			}
			appUser.setUsername(validatedUsername);

			String validatedEmail = validateEmail(appUser.getEmail());
			if(existsByEmail(validatedEmail) && getIdByEmail(validatedEmail) != appUser.getId()) {
				throw new Exception("AppUser: Email already registered");
			}
			appUser.setEmail(validatedEmail);

			String validatedPassword = validatePassword(appUser.getPassword());
			appUser.setPassword(encoder.encode(validatedPassword));

			if(appUser.getRoles() == null) {
				Set<Roles> defaultRoles = roleService.setDefaultRoles();
				appUser.setRoles(defaultRoles);
			}

		}catch (Exception e) {
			logger.error(e.getMessage());
		}

		appUser.setUpdated(LocalDateTime.now());

		logger.info("AppUser: added/updated sucessfully");

		return appUserRepository.save(appUser);
	}

	//Retrieve
	@Override
	public List<AppUser> getAllAppUsers() 
	{
		List<AppUser> appUsers = new ArrayList<>();
		appUserRepository.findAll().forEach(appUsers::add);

		return appUsers;
	}

	@Override
	public AppUser getAppUser(long id) 
	{
		return appUserRepository.findById(id)
				.orElseThrow();
	}

	@Override
	public Long getIdByUsername(String username) 
	{
		try {
			username = validateUsername(username);
		}catch (Exception e) {
			logger.error(e.getMessage());
		}

		return appUserRepository.findByUsername(username).get().getId();
	}

	private Long getIdByEmail(String email) 
	{
		return appUserRepository.findByEmail(email).get().getId();
	}

	//Update
	@Override
	public AppUser updateAppUser(AppUser appUser)
	{
		Optional<AppUser> dbUser = appUserRepository.findById(appUser.getId());

		if(dbUser.isPresent())
		{
			try {
				String validatedUsername = validateUsername(appUser.getUsername());

				if(!validatedUsername.equals(dbUser.get().getUsername())) {
					Set<Roles> resetedRoles = roleService.resetUsernameRoles(
							dbUser.get().getRoles(), 
							dbUser.get().getUsername(), 
							validatedUsername);

					dbUser.get().setRoles(resetedRoles);
				}

				dbUser.get().setUsername(validatedUsername);
			}catch (Exception e) {
				logger.error(e.getMessage());
			}

			dbUser.get().setEmail(appUser.getEmail());
			dbUser.get().setPassword(appUser.getPassword());

			dbUser.get().setNickname(appUser.getNickname());
			dbUser.get().setName(appUser.getName());
			dbUser.get().setDateOfBirth(appUser.getDateOfBirth());
			dbUser.get().setIsSexMale(appUser.getIsSexMale());

			addAppUser(dbUser.get());

			dbUser = appUserRepository.findById(appUser.getId());
		}

		return dbUser.orElseThrow();
	}

	//Delete
	@Override
	public void deleteUser(long id) 
	{
		appUserRepository.deleteById(id);
	}

	@Override
	public Boolean existsByUsername(String user) {
		return appUserRepository.existsByUsername(user);
	}

	@Override
	public Boolean existsByEmail(String email) {
		return appUserRepository.existsByEmail(email);
	}

	public String validateUsername(String username) throws Exception {
		String validate = username;

		if(!isUsername(username)) {
			throw new Exception("Erro: Nome de usuário só pode conter letras maiúsculas, minuscúlas e algarismos de 0 a 9.");
		}

		validate = validate.toUpperCase();

		return validate;
	}

	private String validateEmail(String email) throws Exception {
		String validate = email;

		if(!isEmail(validate)) {
			throw new Exception("Erro: Formato de e-mail incorreto.");
		}

		return validate;
	}

	private String validatePassword(String password) throws Exception {
		String validate = password;

		/* WHILE DEV
		if(!RegexUtils.isPassword(password)) {
			throw new Exception("Erro: Senha precisa conter pelo menos um dígito [0-9].\n"
					+ "Senha precisa conter pelo menos um caracter minúsculo [a-z].\n"
					+ "Senha precisa conter pelo menos um caracter maiúsculo [A-Z].\n"
					+ "Senha precisa conter pelo menos um caracter especial [! @ # & ( )].\n"
					+ "Senha precisa conter de 5 a 20 caracteres.");
		}
		 */

		return validate;
	}
	
	private static Boolean isUsername(String username) {
		if (username == null) return false;
		return Pattern
				//Só pode conter letras maiúsculas, minuscúlas e algarismos de 0 a 9
				.compile("[a-zA-Z0-9]*")
				.matcher(username)
				.matches();
	}

	private static Boolean isEmail(String email) {
		if (email == null) return false;
		return Pattern
				//E-mail validation by RFC5322 standards: https://www.rfc-editor.org/info/rfc5322
				.compile("^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$")
				.matcher(email)
				.matches();
	}

	private static Boolean isPassword(String password) {
		if (password == null) return false;

		Integer minChars = 5;
		Integer maxChars = 20;

		return Pattern
				/*
				https://mkyong.com/regular-expressions/how-to-validate-password-with-regular-expression/ 

				Password must contain at least one digit [0-9].
				Password must contain at least one lowercase Latin character [a-z].
				Password must contain at least one uppercase Latin character [A-Z].
				Password must contain at least one special character like ! @ # & ( ).
				Password must contain a length of at least 8 characters and a maximum of 20 characters.
				 */
				.compile("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#&()–[{}]:;',?/*~$^+=<>]).{"
						+ minChars.toString()
						+ ","
						+ maxChars.toString()
						+ "}$"
						)
				.matcher(password)
				.matches();
	}
}
