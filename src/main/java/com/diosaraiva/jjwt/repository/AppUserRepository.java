package com.diosaraiva.jjwt.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.diosaraiva.jjwt.entity.AppUser;

public interface AppUserRepository extends CrudRepository<AppUser, Long> {
	
	Optional<AppUser> findByUsername(String username);
	
	Optional<AppUser> findByEmail(String email);

	Boolean existsByUsername(String username);

	Boolean existsByEmail(String email);
}
