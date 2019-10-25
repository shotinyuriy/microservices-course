package com.gridu.microservice.taxes.service;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

@Service
@Profile("entity-manager")
public class EntityManagerDataService implements InitializingBean {

	@Override
	public void afterPropertiesSet() throws Exception {
		System.out.println("entity-manager profile");

	}

}
