package com.diosaraiva.jjwt.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class RolesDto {

	private Long id;

	private String role;

	private LocalDateTime updated;
}
