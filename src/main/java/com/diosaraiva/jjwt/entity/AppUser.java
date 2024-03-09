package com.diosaraiva.jjwt.entity;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Set;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AppUser implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@ManyToMany
	private Set<Roles> roles;

	private String username;
	private String email;
	private String password;						//senha

	private String nickname;						//apelido
	
	private String name;							//nome
	private LocalDateTime dateOfBirth;				//dataNascimento
	private Boolean isSexMale;						//sexoMasculino

	private LocalDateTime updated;

	private String jsonAttachs;
}
