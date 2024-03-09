package com.diosaraiva.jjwt.service;

import java.util.List;

import com.diosaraiva.jjwt.entity.AppUser;

public interface AppUserService {

	//Create
	AppUser addAppUser(AppUser appUser);

	//Retrieve
	List<AppUser> getAllAppUsers();

	AppUser getAppUser(long id);

	Long getIdByUsername(String username);

	//Update
	AppUser updateAppUser(AppUser appUser);

	//Delete
	void deleteUser(long id);

	Boolean existsByUsername(String username);
	Boolean existsByEmail(String email);
	
	String validateUsername(String username) throws Exception;
}
