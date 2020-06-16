package com.gridu.microservices.product.catalog.rest;

import java.util.ArrayList;
import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gridu.microservices.product.catalog.exceptions.UniqueObjectViolationException;
import com.gridu.microservices.product.catalog.model.Product;
import com.gridu.microservices.product.catalog.model.ProductCategory;
import com.gridu.microservices.product.catalog.model.ProductSku;
import com.gridu.microservices.product.catalog.rest.model.ProductRestModel;
import com.gridu.microservices.product.catalog.rest.model.ProductRestModel.SkuModel;
import com.gridu.microservices.product.catalog.service.CurrencyService;
import com.gridu.microservices.product.catalog.service.ProductCategoryService;
import com.gridu.microservices.product.catalog.service.ProductService;
import com.gridu.microservices.product.catalog.service.SkuTypeService;
import com.gridu.microservices.product.catalog.transformer.ConstraintViolationTransformer;
import com.gridu.microservices.product.catalog.transformer.ProductTransformer;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.groups.Default;
import javax.validation.Validator;

@RestController
@RequestMapping("/catalog/products")
public class ProductCatalogRestResourceV1 {

	@Autowired
	private ConstraintViolationTransformer constraintViolationTransformer;

	@Autowired
	private CurrencyService currencyService;

	@Autowired
	private ProductCategoryService productCategoryService;

	@Autowired
	private ProductService productService;

	@Autowired
	private ProductTransformer productTransformer;

	@Autowired
	private SkuTypeService skuTypeService;

	@Autowired
	private LocalValidatorFactoryBean validationService;

	@PostMapping(produces = "application/json")
	public ResponseEntity<?> addCatalogProducts(@RequestBody ProductRestModel productModel) {
		Product product = getProductTransformer().createProduct(productModel);
		Product savedProduct = getProductService().save(product);
		return ResponseEntity.status(HttpStatus.CREATED)
				.body(Collections.singletonMap("link", "/catalog/products/" + savedProduct.getId()));
	}

	@DeleteMapping(value = "/{productId}", produces = "application/json")
	public ResponseEntity<?> deleteProduct(@PathVariable(value = "productId") Long productId) {
		HttpStatus status;
		if (getProductService().existsById(productId)) {
			getProductService().deleteById(productId);
			status = HttpStatus.NO_CONTENT;
		} else {
			status = HttpStatus.NOT_FOUND;
		}
		return ResponseEntity.status(status).build();
	}

	@GetMapping(produces = "application/json")
	public ResponseEntity<?> getCatalogProducts() {
		Iterable<Product> products = getProductService().findAll();
		List<ProductRestModel> productResponse = new ArrayList<>();
		for (Product product : products) {
			productResponse.add(getProductTransformer().createProductRestModel(product, false));
		}
		return ResponseEntity.ok(productResponse);
	}

	@GetMapping(value = "/{productId}", produces = "application/json")
	public ResponseEntity<?> getProduct(@PathVariable(value = "productId") Long productId) {
		Optional<Product> product = getProductService().findById(productId);

		HttpStatus status;
		ProductRestModel restModel;
		if (product.isPresent()) {
			status = HttpStatus.OK;
			restModel = getProductTransformer().createProductRestModel(product.get(), true);
		} else {
			status = HttpStatus.NOT_FOUND;
			restModel = null;
		}
		return ResponseEntity.status(status).body(restModel);
	}

	@GetMapping(value = "/productCategories")
	public ResponseEntity<?> getProductCategories() {
		Iterable<ProductCategory> productCategories = getProductCategoryService().findAll();
		List<String> categories = new ArrayList<String>();
		for (ProductCategory productCategory : productCategories) {
			categories.add(productCategory.getName());
		}
		return ResponseEntity.status(HttpStatus.OK).body(categories);
	}

	@PatchMapping(value = "/{productId}", produces = "application/json")
	public ResponseEntity<?> patchProduct(@PathVariable(value = "productId") Long productId,
			@RequestBody ProductRestModel productModel) {
		HttpStatus status;
		Object responseBody = null;

		Set<ConstraintViolation<ProductRestModel>> constraintViolations = getValidationService().validate(productModel,
				Default.class);
		if (!constraintViolations.isEmpty()) {
			status = HttpStatus.BAD_REQUEST;
			responseBody = getConstraintViolationTransformer().getValidationResult(constraintViolations);
			return ResponseEntity.status(status).body(responseBody);
		}

		Optional<Product> product = getProductService().findById(productId);
		if (product.isPresent()) {
			updateProduct(product.get(), productModel);
			status = HttpStatus.OK;
		} else {
			status = HttpStatus.NOT_FOUND;
		}
		return ResponseEntity.status(status).body(responseBody);
	}

	private void checkConsistency(Product product, ProductRestModel productModel) {
		if (getProductService().findUniqueProduct(productModel.getName(), product.getCategory().getName()) != null) {
			throw new UniqueObjectViolationException(
					String.format("Product with name %s already exists in %s category.", productModel.getName(),
							productModel.getCategory()));
		}
	}

	private ProductSku createProductSku(SkuModel sku) {
		ProductSku prodSku = new ProductSku();
		setSkuType(sku, prodSku);
		setSkuValue(sku, prodSku);
		return prodSku;
	}

	private ConstraintViolationTransformer getConstraintViolationTransformer() {
		return constraintViolationTransformer;
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

	private ProductTransformer getProductTransformer() {
		return productTransformer;
	}

	private SkuTypeService getSkuTypeService() {
		return skuTypeService;
	}

	private Validator getValidationService() {
		return validationService.getValidator();
	}

	private void setSkuType(SkuModel sku, ProductSku prodSku) {
		String skuType = sku.getSkus().keySet().iterator().next();
		prodSku.setSkuType(getSkuTypeService().findByValue(skuType));
	}

	private void setSkuValue(SkuModel sku, ProductSku prodSku) {
		String skuValue = sku.getSkus().values().iterator().next();
		prodSku.setSkuValue(skuValue);
	}

	private void updateProduct(Product product, ProductRestModel productModel) {

		checkConsistency(product, productModel);
		product.setName(productModel.getName());
		product.setPrice(productModel.getPrice());
		product.setCurrency(getCurrencyService().findByValue(productModel.getCurrency()));

		for (SkuModel sku : productModel.getSkus()) {
			updateProductSkus(product, sku);
		}
		getProductService().save(product);
	}

	private void updateProductSkus(Product product, SkuModel sku) {
		if (sku.getId() == null) {
			ProductSku prodSku = createProductSku(sku);
			product.addChildSkus(prodSku);
		} else {
			ProductSku skuById = getProductService().findSkuById(product.getId(), sku.getId());
			setSkuType(sku, skuById);
			setSkuValue(sku, skuById);
		}
	}
}
