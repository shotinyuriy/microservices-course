package com.gridu.microservice.taxes;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ImportResource;

@SpringBootApplication
@EnableDiscoveryClient
@ImportResource("file:src/main/webapp/config/application-context.xml")
public class TaxesCalculationApplication {

	public static void main(String[] args) {
		SpringApplication.run(TaxesCalculationApplication.class, args);
	}
}