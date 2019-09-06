package com.gridu.microservices.productcatalog.integration;

import com.gridu.microservices.productcatalog.data.model.Product;
import com.gridu.microservices.productcatalog.data.model.ProductCategory;
import com.gridu.microservices.productcatalog.rest.ProductCatalogRestResourceV1;
import com.gridu.microservices.productcatalog.rest.model.AddProductResponse;
import com.gridu.microservices.productcatalog.rest.model.ProductRequest;
import com.gridu.microservices.productcatalog.rest.model.SkuRequest;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductCatalogApplicationTests {

	@Autowired
	private ProductCatalogRestResourceV1 productCatalogRestResourceV1;

	@Test
	public void testProductCrud() {
		// READ
		ResponseEntity<List<Product>> responseEntity = productCatalogRestResourceV1.getProducts();
		Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		Assert.assertNotNull(responseEntity);
		Assert.assertNotNull(responseEntity.getBody());

		int sizeBeforeAdd = responseEntity.getBody().size();

		// CREATE
		ProductRequest productRequest = new ProductRequest();
		productRequest.setName("Spring in Action");
		productRequest.setPrice(24.99);
		productRequest.setCategory("books1");
		productRequest.setChildSkus(new ArrayList<>());
		productRequest.getChildSkus().add(new SkuRequest());

		ResponseEntity<?> responseEntityAdd = productCatalogRestResourceV1.addProduct(productRequest);
		Assert.assertEquals(HttpStatus.BAD_REQUEST, responseEntityAdd.getStatusCode());

		productRequest.setCategory("books");

		responseEntityAdd = productCatalogRestResourceV1.addProduct(productRequest);
		Assert.assertEquals(HttpStatus.CREATED, responseEntityAdd.getStatusCode());
		Assert.assertNotNull(responseEntityAdd);
		Assert.assertNotNull(responseEntityAdd.getBody());
		Assert.assertTrue(responseEntityAdd.getBody() instanceof AddProductResponse);
		AddProductResponse productResponse = (AddProductResponse) responseEntityAdd.getBody();
		Assert.assertNotNull(productResponse.getId());

		// READ
		ResponseEntity<?> responseEntityById = productCatalogRestResourceV1.getProductById(productResponse.getId());
		Assert.assertEquals(HttpStatus.OK, responseEntityById.getStatusCode());
		Assert.assertNotNull(responseEntityById);
		Assert.assertNotNull(responseEntityById.getBody());
		Product product = (Product) responseEntityById.getBody();
		Assert.assertNotNull(product.getChildSkus());
		Assert.assertEquals(1, product.getChildSkus().size());

		// DELETE
		ResponseEntity<?> responseEntityDelete = productCatalogRestResourceV1.deleteProduct(productResponse.getId());
		Assert.assertNotNull(responseEntityDelete);
		Assert.assertEquals(HttpStatus.NO_CONTENT, responseEntityDelete.getStatusCode());
	}

	@Test
	public void testClothingProductCrud() {
		// READ
		ResponseEntity<List<Product>> responseEntity = productCatalogRestResourceV1.getProducts();
		Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		Assert.assertNotNull(responseEntity);
		Assert.assertNotNull(responseEntity.getBody());

		int sizeBeforeAdd = responseEntity.getBody().size();

		// CREATE CLOTHING SKU
		ProductRequest productRequest = new ProductRequest();
		productRequest.setName("Superman T-Shirt");
		productRequest.setPrice(24.99);
		productRequest.setCategory(ProductCategory.CLOTHING);
		productRequest.setChildSkus(new ArrayList<>());
		SkuRequest skuRequest1 = new SkuRequest();
		skuRequest1.setSize("XS");
		SkuRequest skuRequest2 = new SkuRequest();
		skuRequest2.setSize("S");
		productRequest.getChildSkus().add(skuRequest1);
		productRequest.getChildSkus().add(skuRequest2);

		ResponseEntity<?> responseEntityAdd = productCatalogRestResourceV1.addProduct(productRequest);
		Assert.assertEquals(HttpStatus.CREATED, responseEntityAdd.getStatusCode());
		Assert.assertNotNull(responseEntityAdd);
		Assert.assertNotNull(responseEntityAdd.getBody());
		Assert.assertTrue(responseEntityAdd.getBody() instanceof AddProductResponse);
		AddProductResponse productResponse = (AddProductResponse) responseEntityAdd.getBody();
		Assert.assertNotNull(productResponse.getId());

		// READ
		ResponseEntity<?> responseEntityById = productCatalogRestResourceV1.getProductById(productResponse.getId());
		Assert.assertNotNull(responseEntityById);
		Assert.assertEquals(HttpStatus.OK, responseEntityById.getStatusCode());
		Assert.assertNotNull(responseEntityById.getBody());
		Product product = (Product) responseEntityById.getBody();
		Assert.assertNotNull(product.getChildSkus());
		Assert.assertEquals(2, product.getChildSkus().size());

		// UPDATE
		ProductRequest productRequestUpdate = new ProductRequest();
		productRequestUpdate.setName(product.getName() + " New");
		productRequestUpdate.setPrice(product.getPrice());
		List<SkuRequest> skuRequests = product.getChildSkus().stream()
			.map(sku -> {
				SkuRequest skuRequest = new SkuRequest();
				skuRequest.setId(sku.getId());
				return skuRequest;
			})
			.collect(Collectors.toList());
		productRequestUpdate.setChildSkus(skuRequests);
		SkuRequest newSku = new SkuRequest();
		newSku.setSize("M");
		productRequestUpdate.getChildSkus().add(newSku);

		ResponseEntity responseEntityUpdate = productCatalogRestResourceV1.updateProduct(product.getId(), productRequestUpdate);
		Assert.assertNotNull(responseEntityUpdate);
		Assert.assertEquals(HttpStatus.OK, responseEntityUpdate.getStatusCode());

		// READ AFTER UPDATE
		ResponseEntity<?> responseEntityAfterUpdate = productCatalogRestResourceV1.getProductById(productResponse.getId());
		Assert.assertNotNull(responseEntityAfterUpdate);
		Assert.assertEquals(HttpStatus.OK, responseEntityAfterUpdate.getStatusCode());
		Assert.assertNotNull(responseEntityAfterUpdate.getBody());
		Product productAfterUpdate = (Product) responseEntityAfterUpdate.getBody();
		Assert.assertEquals(product.getName() + " New", productAfterUpdate.getName());
		Assert.assertNotNull(productAfterUpdate.getChildSkus());
		Assert.assertEquals(3, productAfterUpdate.getChildSkus().size());

		// DELETE
		ResponseEntity<?> responseEntityDelete = productCatalogRestResourceV1.deleteProduct(productResponse.getId());
		Assert.assertNotNull(responseEntityDelete);
		Assert.assertEquals(HttpStatus.NO_CONTENT, responseEntityDelete.getStatusCode());
	}

	@Test
	public void testElectronicDeviceCrud() {
		// READ
		ResponseEntity<List<Product>> responseEntity = productCatalogRestResourceV1.getProducts();
		Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		Assert.assertNotNull(responseEntity);
		Assert.assertNotNull(responseEntity.getBody());

		int sizeBeforeAdd = responseEntity.getBody().size();

		// CREATE CLOTHING SKU
		ProductRequest productRequest = new ProductRequest();
		productRequest.setName("Champion Watches");
		productRequest.setPrice(24.99);
		productRequest.setCategory("electronic devices");
		productRequest.setChildSkus(new ArrayList<>());
		SkuRequest skuRequest1 = new SkuRequest();
		skuRequest1.setColor("White");
		SkuRequest skuRequest2 = new SkuRequest();
		skuRequest2.setColor("Black");
		productRequest.getChildSkus().add(skuRequest1);
		productRequest.getChildSkus().add(skuRequest2);

		ResponseEntity<?> responseEntityAdd = productCatalogRestResourceV1.addProduct(productRequest);
		Assert.assertEquals(HttpStatus.CREATED, responseEntityAdd.getStatusCode());
		Assert.assertNotNull(responseEntityAdd);
		Assert.assertNotNull(responseEntityAdd.getBody());
		Assert.assertTrue(responseEntityAdd.getBody() instanceof AddProductResponse);
		AddProductResponse productResponse = (AddProductResponse) responseEntityAdd.getBody();
		Assert.assertNotNull(productResponse.getId());

		// READ
		ResponseEntity<?> responseEntityById = productCatalogRestResourceV1.getProductById(productResponse.getId());
		Assert.assertNotNull(responseEntityById);
		Assert.assertEquals(HttpStatus.OK, responseEntityById.getStatusCode());
		Assert.assertNotNull(responseEntityById.getBody());
		Product product = (Product) responseEntityById.getBody();
		Assert.assertNotNull(product.getChildSkus());
		Assert.assertEquals(2, product.getChildSkus().size());

		// UPDATE
		ProductRequest productRequestUpdate = new ProductRequest();
		productRequestUpdate.setName(product.getName()+" New");
		productRequestUpdate.setPrice(product.getPrice());
		List<SkuRequest> skuRequests = product.getChildSkus().stream()
			.map(sku -> {
				SkuRequest skuRequest = new SkuRequest();
				skuRequest.setId(sku.getId());
				return skuRequest;
			})
			.collect(Collectors.toList());
		productRequestUpdate.setChildSkus(skuRequests);
		SkuRequest newSku = new SkuRequest();
		newSku.setColor("Gold");
		productRequestUpdate.getChildSkus().add(newSku);

		ResponseEntity responseEntityUpdate = productCatalogRestResourceV1.updateProduct(product.getId(), productRequestUpdate);
		Assert.assertNotNull(responseEntityUpdate);
		Assert.assertEquals(HttpStatus.OK, responseEntityUpdate.getStatusCode());

		// READ AFTER UPDATE
		ResponseEntity<?> responseEntityAfterUpdate = productCatalogRestResourceV1.getProductById(productResponse.getId());
		Assert.assertNotNull(responseEntityAfterUpdate);
		Assert.assertEquals(HttpStatus.OK, responseEntityAfterUpdate.getStatusCode());
		Assert.assertNotNull(responseEntityAfterUpdate.getBody());
		Product productAfterUpdate = (Product) responseEntityAfterUpdate.getBody();
		Assert.assertEquals(product.getName()+" New", productAfterUpdate.getName());
		Assert.assertNotNull(productAfterUpdate.getChildSkus());
		Assert.assertEquals(3, productAfterUpdate.getChildSkus().size());

		// DELETE
		ResponseEntity<?> responseEntityDelete = productCatalogRestResourceV1.deleteProduct(productResponse.getId());
		Assert.assertNotNull(responseEntityDelete);
		Assert.assertEquals(HttpStatus.NO_CONTENT, responseEntityDelete.getStatusCode());
	}
}
