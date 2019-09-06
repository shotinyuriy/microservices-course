package com.gridu.microservices.productcatalog;

import com.gridu.microservice.rest.validation.ErrorResponseTransformer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.client.RestTemplate;

//@EnableAutoConfiguration(exclude = {DataSourceAutoConfiguration.class})
@SpringBootApplication(exclude = { SecurityAutoConfiguration.class})
@ComponentScan("com.gridu.microservices.productcatalog")
@EntityScan("com.gridu.microservices.productcatalog.data.model")
@EnableScheduling
public class ProductCatalogApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProductCatalogApplication.class, args);
	}

	@Bean
	public ErrorResponseTransformer errorResponseTransformer() {
		return new ErrorResponseTransformer();
	}

	@Bean
	public RestTemplate restTemplate() {
		return new RestTemplate();
	}
}
