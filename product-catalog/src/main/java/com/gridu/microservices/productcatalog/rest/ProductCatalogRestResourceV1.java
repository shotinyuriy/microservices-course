package com.gridu.microservices.productcatalog.rest;

import com.gridu.microservices.productcatalog.data.model.Product;
import com.gridu.microservices.productcatalog.data.service.ProductService;
import com.gridu.microservices.productcatalog.rest.model.ProductRequest;
import com.gridu.microservices.productcatalog.rest.transformer.ProductTransformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/catalog")
public class ProductCatalogRestResourceV1 {

	@Autowired
	private ProductService productService;

	@Autowired
	private ProductTransformer productTransformer;

	@GetMapping("/products")
	public ResponseEntity<List<Product>> getProducts() {

		List<Product> products = new ArrayList<>();
		productService.findAll().forEach(products::add);

		return ResponseEntity.ok(products);
	}

	@PostMapping("/products")
	public ResponseEntity<Object> addProduct(@RequestBody ProductRequest productRequest) {

		Product newProduct = productTransformer.fromProductRequest(productRequest);
		Product savedProduct = productService.save(newProduct);

		URI savedProductUri = URI.create("/products/"+product.getId());
		return ResponseEntity.created(savedProductUri).build();
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
}
