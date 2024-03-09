package com.diosaraiva.jjwt.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.diosaraiva.jjwt.entity.Roles;

public interface RolesRepository extends CrudRepository<Roles, Long> {
	
	Optional<Roles> findByRole(String role);
	
	Boolean existsByRole(String role);
}
