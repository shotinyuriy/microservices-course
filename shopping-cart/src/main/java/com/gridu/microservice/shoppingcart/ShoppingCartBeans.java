package com.gridu.microservice.shoppingcart;

import com.gridu.microservice.rest.validation.ErrorResponseTransformer;
import com.gridu.microservice.shoppingcart.rest.model.ProductResponse;
import com.gridu.microservice.shoppingcart.rest.model.ShoppingCartSessionContainer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.web.context.annotation.SessionScope;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class ShoppingCartBeans {

	@Value("${shoppingCart.redis.connectionFactory}")
	String redisConnectionFactory;

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
		if ("jedis".equalsIgnoreCase(redisConnectionFactory)) {
			JedisConnectionFactory connectionFactory = new JedisConnectionFactory();
			return connectionFactory;
		} else {
			LettuceConnectionFactory connectionFactory = new LettuceConnectionFactory();
			return connectionFactory;
		}
	}

	@Bean
	public StringRedisSerializer stringRedisSerializer() {
		StringRedisSerializer redisSerializer = new StringRedisSerializer();
		return redisSerializer;
	}

	@Bean
	public RedisSerializer<ProductResponse> hashValueRedisSerializer() {
		Jackson2JsonRedisSerializer redisSerializer = new Jackson2JsonRedisSerializer(ProductResponse.class);
		return redisSerializer;
	}

	@Bean
	public RedisTemplate<String, ProductResponse> productRedisTemplate() {
		RedisTemplate<String, ProductResponse> redisTemplate = new RedisTemplate<>();
		redisTemplate.setConnectionFactory(redisConnectionFactory());

		redisTemplate.setKeySerializer(stringRedisSerializer());
		redisTemplate.setHashKeySerializer(stringRedisSerializer());
		redisTemplate.setHashValueSerializer(hashValueRedisSerializer());
		return redisTemplate;
	}

	@Bean
	public RedisTemplate<String, ProductResponse> redisTemplate1() {
		RedisTemplate<String, ProductResponse> redisTemplate = new RedisTemplate<>();
		redisTemplate.setConnectionFactory(redisConnectionFactory());
		return redisTemplate;
	}
}