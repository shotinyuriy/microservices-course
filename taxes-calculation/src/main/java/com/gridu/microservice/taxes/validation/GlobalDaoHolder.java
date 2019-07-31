package com.gridu.microservice.taxes.validation;

import com.gridu.microservice.taxes.dao.TaxCategoryDao;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * This class uses a little hack of ApplicationContextAware inteface to be able to provide dao classes
 * for the different Validator classes
 * so that they can check existence of the entities in the database
 */
@Component
public class GlobalDaoHolder implements ApplicationContextAware {

	private static TaxCategoryDao taxCategoryDao;

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		taxCategoryDao = applicationContext.getBean(TaxCategoryDao.class);
	}

	public static TaxCategoryDao getTaxCategoryDao() {
		return taxCategoryDao;
	}
}
