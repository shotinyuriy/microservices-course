package com.gridu.microservices.productcatalog.service;

import com.gridu.microservices.productcatalog.dao.ProductRepository;
import com.gridu.microservices.productcatalog.model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Created by anasimijonovic on 8/20/19.
 */
@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    public List<Product> getAllProducts() {
        List<Product> products = new ArrayList<>();
        productRepository.findAll().forEach(products::add);

        return products;
    }

    public Product getById(Long id) {
        return productRepository.findById(id).orElse(null);
    }

    public Optional<Product> getProductsByName(String name) {
        return productRepository.findByName(name);
    }

    public Product addProduct(Product product) {
        return productRepository.save(product);
    }

    public void updateProduct(Product product) {
        productRepository.save(product);
    }

    public void deleteProduct(Long productId) {
        productRepository.deleteById(productId);
    }
}
