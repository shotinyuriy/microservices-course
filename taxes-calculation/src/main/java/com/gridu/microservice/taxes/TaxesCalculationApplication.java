package com.gridu.microservice.taxes;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ImportResource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * Created by anasimijonovic on 8/28/19.
 */
@SpringBootApplication(exclude = { DataSourceAutoConfiguration.class, HibernateJpaAutoConfiguration.class })
@ComponentScan(basePackages = "com.gridu.microservice")
@EnableJpaRepositories("com.gridu.microservice.taxes.dao")
@EntityScan("com.gridu.microservice.taxes.model")
@ImportResource("application-context.xml")
@EnableEurekaClient
public class TaxesCalculationApplication {

    public static void main(String[] args) {
        SpringApplication.run(TaxesCalculationApplication.class, args);
    }
}
