package com.diosaraiva.jjwt.service;

import java.util.List;
import java.util.Set;

import com.diosaraiva.jjwt.entity.Roles;

public interface RoleService {

	//Create
	Roles addRole(Roles role);

	//Retrieve
	List<Roles> getAllRoles();

	Roles getRole(long id);
	
	Roles findByRole(String string);

	//Update
	Roles updateRole(Roles role);

	//Delete
	void deleteRole(long id);
	
	Boolean existsByRole(String role);
	
	Set<Roles> setDefaultRoles();
	Set<Roles> resetUsernameRoles(Set<Roles> currentRoles, String oldUsername, String newUsername);
}
