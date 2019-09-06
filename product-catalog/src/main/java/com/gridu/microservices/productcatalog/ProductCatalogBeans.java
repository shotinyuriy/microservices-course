package com.gridu.microservices.productcatalog;

import com.gridu.microservice.rest.validation.ErrorResponseTransformer;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class ProductCatalogBeans {

	@LoadBalanced
	@Bean
	public RestTemplate restTemplate() {
		return new RestTemplate();
	}

	@Bean
	public ErrorResponseTransformer errorResponseTransformer() {
		return new ErrorResponseTransformer();
	}
}
