package com.gridu.microservice.shoppingcart;

import com.gridu.microservice.rest.validation.ErrorResponseTransformer;
import com.gridu.microservice.shoppingcart.rest.model.ProductResponse;
import com.gridu.microservice.shoppingcart.rest.model.ShoppingCartSessionContainer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.context.annotation.SessionScope;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class ShoppingCartBeans {

	@Value("${integration.baseUrl.product-catalog}")
	String productCatalogName;

	@Value("${integration.baseUrl.taxes-calculation}")
	String taxesCalculationName;

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
		return WebClient.builder().baseUrl(productCatalogName);
	}

	@Bean
	@LoadBalanced
	public WebClient.Builder taxesCalculationWebClient() {
		return WebClient.builder().baseUrl(taxesCalculationName);
	}

	@Bean
	public RedisConnectionFactory redisConnectionFactory() {
		LettuceConnectionFactory connectionFactory = new LettuceConnectionFactory();
		return connectionFactory;
	}

	@Bean
	public RedisTemplate<String, ProductResponse> redisTemplate() {
		RedisTemplate<String, ProductResponse> redisTemplate = new RedisTemplate<>();
		redisTemplate.setConnectionFactory(redisConnectionFactory());
		return redisTemplate;
	}
}