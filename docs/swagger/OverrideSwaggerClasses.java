package com.udemy.microservice.currencyexchangeservice.swagger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import springfox.documentation.schema.TypeNameExtractor;
import springfox.documentation.spring.web.plugins.DocumentationPluginsManager;

@Configuration
public class OverrideSwaggerClasses {

	// Let Spring create this bean because it has dependencies to inject in constructor
	@Autowired
	private TypeNameExtractorReplaced typeNameExtractorReplaced;

	@Bean
	@Primary
	public DocumentationPluginsManager bean() {
	    return new DocumentationPluginsManagerReplaced();
	}

	@Bean
	@Primary
	public TypeNameExtractor beanTypeName() {
	    return typeNameExtractorReplaced;
	}
}