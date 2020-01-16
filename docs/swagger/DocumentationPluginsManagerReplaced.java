package com.udemy.microservice.currencyexchangeservice.swagger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.plugin.core.PluginRegistry;

import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.DefaultsProviderPlugin;
import springfox.documentation.spi.service.ResourceGroupingStrategy;
import springfox.documentation.spi.service.contexts.DocumentationContextBuilder;
import springfox.documentation.spring.web.SpringGroupingStrategy;
import springfox.documentation.spring.web.plugins.DefaultConfiguration;
import springfox.documentation.spring.web.plugins.DocumentationPluginsManager;

public class DocumentationPluginsManagerReplaced extends DocumentationPluginsManager {
	
	@Autowired
	@Qualifier("resourceGroupingStrategyRegistry")
	private PluginRegistry<ResourceGroupingStrategy, DocumentationType> resourceGroupingStrategies;

	@Autowired
	@Qualifier("defaultsProviderPluginRegistry")
	private PluginRegistry<DefaultsProviderPlugin, DocumentationType> defaultsProviders;

	@Override
	public ResourceGroupingStrategy resourceGroupingStrategy(DocumentationType documentationType) {
		return resourceGroupingStrategies.getPluginOrDefaultFor(documentationType, new SpringGroupingStrategy());
	}

	@Override
	public DocumentationContextBuilder createContextBuilder(DocumentationType documentationType,
			DefaultConfiguration defaultConfiguration) {
		return defaultsProviders.getPluginOrDefaultFor(documentationType, defaultConfiguration)
				.create(documentationType).withResourceGroupingStrategy(resourceGroupingStrategy(documentationType));
	}
}
