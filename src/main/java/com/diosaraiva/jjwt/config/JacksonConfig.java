package com.diosaraiva.jjwt.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLGenerator;
import com.fasterxml.jackson.dataformat.yaml.YAMLMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

@Configuration
public class JacksonConfig {

	public static final ObjectMapper OBJECT_MAPPER = Jackson2ObjectMapperBuilder.json()
			.build();

	public static final YAMLMapper YAML_MAPPER = YAMLMapper.builder()
			.addModule(new JavaTimeModule())
			.disable(YAMLGenerator.Feature.WRITE_DOC_START_MARKER)
			.build();
}
