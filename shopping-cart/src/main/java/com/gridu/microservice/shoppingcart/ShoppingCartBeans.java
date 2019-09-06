package com.gridu.microservice.shoppingcart;

import com.gridu.microservice.rest.validation.ErrorResponseTransformer;
import com.gridu.microservice.shoppingcart.rest.model.ShoppingCartSessionContainer;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.annotation.SessionScope;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class ShoppingCartBeans {

	@Bean
	@SessionScope
	public ShoppingCartSessionContainer shoppingCart() {
		return new ShoppingCartSessionContainer();
	}

	@Bean
	public ErrorResponseTransformer errorResponseTransformer() {
		return new ErrorResponseTransformer();
	}

	@Bean
	@LoadBalanced
	public WebClient.Builder productCatalogWebClient() {
		return WebClient.builder().baseUrl("http://product-catalog");
	}

	@Bean
	@LoadBalanced
	public WebClient.Builder taxesCalculationWebClient() {
		return WebClient.builder().baseUrl("http://taxes-calculation");
	}
}