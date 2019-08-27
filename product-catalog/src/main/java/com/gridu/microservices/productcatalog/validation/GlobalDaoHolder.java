package com.gridu.microservices.productcatalog.validation;

import com.gridu.microservices.productcatalog.service.ProductCategoryService;
import com.gridu.microservices.productcatalog.service.ProductService;
import com.gridu.microservices.productcatalog.service.SkuService;
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

	private static ProductService productService;
	private static ProductCategoryService productCategoryService;
	private static SkuService skuService;

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		productService = applicationContext.getBean(ProductService.class);
		productCategoryService = applicationContext.getBean(ProductCategoryService.class);
		skuService = applicationContext.getBean(SkuService.class);
	}

	public static ProductService getProductService() {
		return productService;
	}

	public static ProductCategoryService getProductCategoryService() {
		return productCategoryService;
	}

	public static SkuService getSkuService() {
		return skuService;
	}
}
