package com.diosaraiva.jjwt.dto;

import java.time.LocalDateTime;
import java.util.Collection;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class AppUserDto {

	private Long id;
	
	@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
	@JsonIdentityReference(alwaysAsId = true)
	private Collection<RolesDto> roles;

	private String username;

	private String email;

	@JsonIgnore
	private String password;						//senha

	private String nickname;						//apelido
	
	private String name;							//nome
	private LocalDateTime dateOfBirth;				//dataNascimento
	private Boolean isSexMale;						//sexoMasculino
	
	private LocalDateTime updated;

	private String jsonAttachs;
}
