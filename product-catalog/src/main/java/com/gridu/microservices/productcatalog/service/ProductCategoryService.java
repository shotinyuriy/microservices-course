package com.gridu.microservices.productcatalog.service;

import com.gridu.microservices.productcatalog.dao.ProductCategoryRepository;
import com.gridu.microservices.productcatalog.model.ProductCategory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Created by anasimijonovic on 8/20/19.
 */
@Service
public class ProductCategoryService {

    @Autowired
    private ProductCategoryRepository productCategoryRepository;

    public List<ProductCategory> getAllProducts() {
        List<ProductCategory> productCategories = new ArrayList<>();
        productCategoryRepository.findAll().forEach(productCategories::add);

        return productCategories;
    }

    public Optional<ProductCategory> getProductCategoriesByName(String name) {
        return productCategoryRepository.findByName(name);
    }

    public void addProductCategory(ProductCategory product) {
        productCategoryRepository.save(product);
    }

    public void updateProductCategory(ProductCategory productCategory) {
        productCategoryRepository.save(productCategory);
    }

    public void deleteProductCategory(Long id) {
        productCategoryRepository.deleteById(id);
    }
}
