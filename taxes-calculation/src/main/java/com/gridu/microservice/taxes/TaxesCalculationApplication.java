package com.gridu.microservice.taxes;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ImportResource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@ComponentScan("com.gridu.microservice.taxes")
@EnableJpaRepositories("com.gridu.microservice.taxes.dao")
@EntityScan("com.gridu.microservice.taxes.model")
@ImportResource(locations={
	      "classpath:application-context.xml"
	     })
@EnableEurekaClient
public class TaxesCalculationApplication {
	
	public static void main(String[] args) {
		SpringApplication.run(TaxesCalculationApplication.class, args);
	}
	
//	@Bean
//	public LocalContainerEntityManagerFactoryBean entityManagerBean() {
//		LocalContainerEntityManagerFactoryBean bean =  new LocalContainerEntityManagerFactoryBean();
//		bean.setPackagesToScan("com.gridu.microservice.taxes.model");
//		bean.setPersistenceUnitName("name");
//	return bean;
//	}
//	
//	@Bean
//	public HibernateJpaVendorAdapter hibernateJpaVendorAdapter() {
//		return new HibernateJpaVendorAdapter();
//	}
	
}


