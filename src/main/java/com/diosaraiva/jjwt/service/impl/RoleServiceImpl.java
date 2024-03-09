package com.diosaraiva.jjwt.service.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.diosaraiva.jjwt.entity.Roles;
import com.diosaraiva.jjwt.repository.RolesRepository;
import com.diosaraiva.jjwt.service.RoleService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService
{
	@Autowired
	RolesRepository rolesRepository;

	//Create
	@Override
	public Roles addRole(Roles role) 
	{
		return rolesRepository.save(role);
	}

	//Retrieve
	@Override
	public List<Roles> getAllRoles() 
	{
		List<Roles> roles = new ArrayList<>();
		rolesRepository.findAll().forEach(roles::add);

		return roles;
	}

	@Override
	public Roles getRole(long id) 
	{
		return rolesRepository.findById(id)
				.orElseThrow();
	}

	@Override
	public Roles findByRole(String string) {
		return rolesRepository.findByRole(string).orElse(null);
	}

	//Update
	@Override
	public Roles updateRole(Roles role) 
	{
		Optional<Roles> dbRole = rolesRepository.findById(role.getId());

		if(dbRole.isPresent())
		{
			addRole(role);

			dbRole = rolesRepository.findById(role.getId());
		}

		return dbRole.orElseThrow();
	}

	//Delete
	@Override
	public void deleteRole(long id) 
	{
		rolesRepository.deleteById(id);
	}

	@Override
	public Boolean existsByRole(String role) {
		return rolesRepository.existsByRole(role);
	}

	@Override
	public Set<Roles> setDefaultRoles() {		
		Set<Roles> defaultRoles = new HashSet<>();
		
		Roles appUserRole = findByRole("ROLE_USER");
		defaultRoles.add(appUserRole);
		
		return defaultRoles;
	}

	@Override
	public Set<Roles> resetUsernameRoles(Set<Roles> currentRoles, String oldUsername, String newUsername) {
		Set<Roles> resetedRoles = currentRoles;

		resetedRoles.forEach(role -> {
			if(role.getRole().contains(oldUsername)) {
				String updatedRole = role.getRole().replace(oldUsername, newUsername);

				role.setRole(updatedRole);
			}
		});

		return resetedRoles;
	}
}