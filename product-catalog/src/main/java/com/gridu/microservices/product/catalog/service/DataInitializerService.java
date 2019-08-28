package com.gridu.microservices.product.catalog.service;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.gridu.microservices.product.catalog.model.Currency;
import com.gridu.microservices.product.catalog.model.Product;
import com.gridu.microservices.product.catalog.model.ProductCategory;
import com.gridu.microservices.product.catalog.model.ProductSku;
import com.gridu.microservices.product.catalog.model.SkuType;

@Component
public class DataInitializerService {

	@Autowired
	private CurrencyService currencyService;

	@Autowired
	private ProductCategoryService productCategoryService;

	@Autowired
	private ProductService productService;

	@Autowired
	private SkuTypeService skuTypeService;

	@PostConstruct
	public void init() {
		Currency usdCurrency = new Currency();
		usdCurrency.setValue("USD");
		getCurrencyService().save(usdCurrency);
		
		Currency eurCurrency = new Currency();
		eurCurrency.setValue("EUR");
		getCurrencyService().save(eurCurrency);
		
		ProductCategory category1  =new ProductCategory();
		category1.setName("books");
		getProductCategoryService().save(category1);
		
		ProductCategory category2  =new ProductCategory();
		category2.setName("clothing");
		getProductCategoryService().save(category2);
		
		ProductCategory category3  =new ProductCategory();
		category3.setName("electronic devices");
		getProductCategoryService().save(category3);
		
		SkuType colorSku = new SkuType();
		colorSku.setValue("color");
		getSkuTypeService().save(colorSku);
		
		SkuType sizeSku = new SkuType();
		sizeSku.setValue("size");
		getSkuTypeService().save(sizeSku);
		
		Product product1 = new Product();
		product1.setCategory(getProductCategoryService().findByName("books"));
		product1.setName("Blindness");
		product1.setPrice(10.67);
		product1.setCurrency(getCurrencyService().findByValue("USD"));
		getProductService().save(product1);
		
		Product product2 = new Product();
		product2.setCategory(getProductCategoryService().findByName("clothing"));
		product2.setName("T-shirts");
		product2.setPrice(7.67);
		product2.setCurrency(getCurrencyService().findByValue("USD"));
		
		ProductSku productSku = new ProductSku();
		productSku.setSkuType(getSkuTypeService().findByValue("size"));
		productSku.setSkuValue("M");
		product2.addChildSkus(productSku);
		getProductService().save(product2);
		
		Product product3 = new Product();
		product3.setCategory(getProductCategoryService().findByName("books"));
		product3.setName("Death with Interruptions");
		product3.setPrice(7.67);
		product3.setCurrency(getCurrencyService().findByValue("USD"));
		getProductService().save(product3);
	}

	private CurrencyService getCurrencyService() {
		return currencyService;
	}

	private ProductCategoryService getProductCategoryService() {
		return productCategoryService;
	}

	private ProductService getProductService() {
		return productService;
	}

	private SkuTypeService getSkuTypeService() {
		return skuTypeService;
	}

}
