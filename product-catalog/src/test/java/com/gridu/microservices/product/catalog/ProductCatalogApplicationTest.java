package com.gridu.microservices.product.catalog;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import com.gridu.microservices.product.catalog.model.Product;
import com.gridu.microservices.product.catalog.rest.ProductCatalogRestResourceV1;
import com.gridu.microservices.product.catalog.rest.model.ProductRestModel;
import com.gridu.microservices.product.catalog.rest.model.ProductRestModel.ProductRestModelBuilder;
import com.gridu.microservices.product.catalog.service.CurrencyService;
import com.gridu.microservices.product.catalog.service.DataInitializerService;
import com.gridu.microservices.product.catalog.service.ProductCategoryService;
import com.gridu.microservices.product.catalog.service.ProductService;
import com.gridu.microservices.product.catalog.service.SkuTypeService;

@RunWith(SpringRunner.class)
@SpringBootTest
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ProductCatalogApplicationTest {

	@Autowired
	private ProductService productService;

	@Autowired
	private ProductCatalogRestResourceV1 controller;

	@Autowired
	private DataInitializerService dataInitService;

	@Autowired
	private ProductCategoryService productCategoryService;

	@Autowired
	private CurrencyService currencyService;

	@Autowired
	private SkuTypeService skuTypeService;

	@Test
	public void contextLoads() {
		assertNotNull(productService);
		assertNotNull(controller);
		assertNotNull(dataInitService);
		assertNotNull(productCategoryService);
		assertNotNull(currencyService);
		assertNotNull(skuTypeService);
	}

	@Test
	public void dataInserted() {
		assertTrue(productService.count() > 0);
		assertTrue(productCategoryService.count() > 0);
		assertTrue(currencyService.count() > 0);
		assertTrue(skuTypeService.count() > 0);
	}

	@Test
	public void getCatalogProducts() {
		ResponseEntity<?> productCategories = controller.getCatalogProducts();
		Object responseBody = productCategories.getBody();
		assertTrue(responseBody instanceof List);
		List<ProductRestModel> restModel = (List<ProductRestModel>) responseBody;
		assertTrue(restModel.size() > 0);
	}

	@Test
	public void addCatalogProduct() {
		long rowNumBeforeUpdate = productService.count();
		ProductRestModel newProduct = new ProductRestModelBuilder().setCategory("books").setCurrency("EUR")
				.setName("New product").setPrice(11.23).build();
		ResponseEntity<?> responseEntity = controller.addCatalogProducts(newProduct);
		assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
		assertEquals(rowNumBeforeUpdate+1, productService.count());		
	}
	
	@Test
	public void deleteCatalogProduct() {
		Iterable<Product> allProducts = productService.findAll();
		Long productId = allProducts.iterator().next().getId();
		assertTrue(productService.findById(productId).isPresent());
		ResponseEntity<?> responseEntity = controller.deleteProduct(productId);
		assertEquals(HttpStatus.NO_CONTENT, responseEntity.getStatusCode());
		assertFalse(productService.findById(productId).isPresent());
	}
}
