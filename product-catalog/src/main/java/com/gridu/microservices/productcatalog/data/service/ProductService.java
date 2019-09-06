package com.gridu.microservices.productcatalog.data.service;

import com.gridu.microservices.productcatalog.data.model.Product;
import com.gridu.microservices.productcatalog.data.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

	@Autowired
	private ProductRepository productRepository;

	public void setProductRepository(ProductRepository productRepository) {
		this.productRepository = productRepository;
	}

	public Product save(Product product) {
		product.getChildSkus().forEach(sku -> sku.setProduct(product));
		return productRepository.save(product);
	}

	public Iterable<Product> findAll() {
		return productRepository.findAll();
	}

	public Optional<Product> findById(String id) {
		return productRepository.findById(id);
	}

	public void deleteById(String productId) {
		productRepository.deleteById(productId);
	}

	public void updateProduct(Product product, Product newProduct) {
		product.setPrice(newProduct.getPrice());
		product.setName(newProduct.getName());
		product.setChildSkus(newProduct.getChildSkus());
		save(product);
	}
}
