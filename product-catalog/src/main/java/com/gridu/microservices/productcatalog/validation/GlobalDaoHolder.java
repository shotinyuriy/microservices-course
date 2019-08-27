package com.gridu.microservices.productcatalog.validation;

import com.gridu.microservices.productcatalog.dao.ProductCategoryRepository;
import com.gridu.microservices.productcatalog.dao.ProductRepository;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * This class uses a little hack of ApplicationContextAware interface to be able to provide dao classes
 * for the different Validator classes
 * so that they can check existence of the entities in the database
 */
@Component
public class GlobalDaoHolder implements ApplicationContextAware {

	private static ProductRepository productRepository;
	private static ProductCategoryRepository productCategoryRepository;

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		productRepository = applicationContext.getBean(ProductRepository.class);
		productCategoryRepository = applicationContext.getBean(ProductCategoryRepository.class);
	}

	public static ProductRepository getProductRepository() {
		return productRepository;
	}

	public static ProductCategoryRepository getProductCategoryRepository() {
		return productCategoryRepository;
	}
}
