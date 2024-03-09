package com.diosaraiva.jjwt.config;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springdoc.core.customizers.OpenApiCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;

@Configuration
@OpenAPIDefinition(security = {@SecurityRequirement(name = "bearer-key")})
public class OpenApiConfig {

	private static Logger logger = LogManager.getLogger(OpenApiConfig.class);

	@Bean
	OpenApiCustomizer customerGlobalHeaderOpenApiCustomizer() {
		logger.info("OpenApiConfig: Authorization Header Component Loaded");

		return openApi -> openApi.getComponents()
				.addSecuritySchemes("bearer-key", new SecurityScheme()
						.type(SecurityScheme.Type.HTTP)
						.scheme("bearer")
						.bearerFormat("JWT"));
	}
}
