package com.gridu.microservices.productcatalog.rest;

import com.gridu.microservice.rest.validation.ErrorResponseTransformer;
import com.gridu.microservice.rest.validation.ValidationResult;
import com.gridu.microservices.productcatalog.data.model.Product;
import com.gridu.microservices.productcatalog.data.service.ProductService;
import com.gridu.microservices.productcatalog.rest.model.AddProductResponse;
import com.gridu.microservices.productcatalog.rest.model.ProductRequest;
import com.gridu.microservices.productcatalog.rest.transformer.ProductTransformer;
import com.gridu.microservices.productcatalog.rest.validation.ValidatorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@RestController
@RequestMapping("/catalog")
public class ProductCatalogRestResourceV1 {

	@Autowired
	private ProductService productService;

	@Autowired
	private ProductTransformer productTransformer;

	@Autowired
	private ValidatorService validatorService;

	@Autowired
	private ErrorResponseTransformer errorResponseTransformer;

	@GetMapping("/products")
	public ResponseEntity<List<Product>> getProducts() {

		List<Product> products = new ArrayList<>();
		productService.findAll().forEach(products::add);

		return ResponseEntity.ok(products);
	}

	@PostMapping("/products")
	public ResponseEntity<Object> addProduct(@RequestBody ProductRequest productRequest) {

		Product newProduct = productTransformer.fromProductRequest(productRequest, null);
		Set<ValidationResult> validationResults = validatorService.validate(newProduct);
		if (!validationResults.isEmpty()) {
			return ResponseEntity.badRequest()
				.body(errorResponseTransformer.fromValidationResults(validationResults));

		}
		Product savedProduct = productService.save(newProduct);

		URI savedProductUri = URI.create("/catalog/products/"+savedProduct.getId());
		AddProductResponse productWithIdOnly = new AddProductResponse(savedProduct.getId());
		return ResponseEntity.created(savedProductUri).body(productWithIdOnly);
	}

	@GetMapping("/products/{productId}")
	public ResponseEntity<Product> getProductById(@PathVariable String productId) {

		Optional<Product> product = productService.findById(productId);
		if(product.isPresent()) {
			return ResponseEntity.ok(product.get());
		} else {
			return ResponseEntity.notFound().build();
		}
	}

	@GetMapping("/search/products")
	public ResponseEntity<Product> searchProductBySkuId(@RequestParam("skuId") String skuId) {
		Product product = productService.findBySkuId(skuId);
		return ResponseEntity.ok(product);
	}

	@PatchMapping("/products/{productId}")
	public ResponseEntity<Object> updateProduct(@PathVariable String productId,
	                                             @RequestBody ProductRequest productRequest) {

		Optional<Product> product = productService.findById(productId);
		if(product.isPresent()) {
			Product newProduct = productTransformer.fromProductRequest(productRequest, product.get());
			Set<ValidationResult> validationResults = validatorService.validate(newProduct);
			if (!validationResults.isEmpty()) {
				return ResponseEntity.badRequest()
					.body(errorResponseTransformer.fromValidationResults(validationResults));

			}
			productService.updateProduct(product.get(), newProduct);
			return ResponseEntity.ok().build();
		} else {
			return ResponseEntity.notFound().build();
		}
	}

	@DeleteMapping("products/{productId}")
	public ResponseEntity<Object> deleteProduct(@PathVariable String productId) {
		Optional<Product> product = productService.findById(productId);
		if (product.isPresent()) {
			productService.deleteById(productId);
			return ResponseEntity.noContent().build();
		} else {
			return ResponseEntity.notFound().build();
		}
	}
}
