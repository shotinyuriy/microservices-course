package com.gridu.microservices.productcatalog.rest;

import com.gridu.microservices.productcatalog.model.Product;
import com.gridu.microservices.productcatalog.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by anasimijonovic on 8/19/19.
 */
@RestController
@RequestMapping("/product")
public class ProductCatalogueController {

    @Autowired
    private ProductService productService;

    @GetMapping(value = "/getAll")
    public String getAll() {
        Product product = new Product();
        product.setName("chocolate");

        if (productService.getProductsByName(product.getName()).size() == 0) {
            productService.addProduct(product);
        }

        List<Product> productList = productService.getAllProducts();

        productList.forEach(System.out::println);

        return "test";
    }
}
