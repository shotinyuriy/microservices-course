package com.gridu.microservices.productcatalog.integration;

import com.gridu.microservices.productcatalog.ProductCatalogApplication;
import com.gridu.microservices.productcatalog.model.ElectronicDeviceSku;
import com.gridu.microservices.productcatalog.model.Product;
import com.gridu.microservices.productcatalog.rest.ProductCatalogueController;
import com.gridu.microservices.productcatalog.rest.model.ProductModel;
import com.gridu.microservices.productcatalog.service.ProductService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;
import java.time.Instant;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;

/**
 * Created by anasimijonovic on 8/23/19.
 */
@SpringBootTest(
        classes = ProductCatalogApplication.class)
@TestPropertySource(
        locations = "classpath:application.properties")
@RunWith(SpringRunner.class)
public class ProductCatalogueTest {

    @Autowired
    private ProductCatalogueController controller;

    @Autowired
    private ProductService productService;

    @Before
    public void setUp() {
        assertNotNull(controller);
        assertNotNull(productService);
    }

    @Test
    public void productCatalogueController_addNewProductTest() {
        String newProductName = "Product #" + Instant.now().toEpochMilli();

        assertFalse("Product name already exists", productService.getProductsByName(newProductName).isPresent());

        ProductModel request = new ProductModel();
        request.setName(newProductName);
        request.setPrice(99.99);
        request.setCategory("electronic device");

        // ---------  creating child skus
        List<Map<String, String>> childSkus = new ArrayList<>();
        Map<String, String> skuModel1 = new HashMap<String, String>(){{
                put("color", "white");
        }};
        Map<String, String> skuModel2 = new HashMap<String, String>(){{
                put("color", "blue");
        }};
        Map<String, String> skuModel3 = new HashMap<String, String>(){{
                put("color", "pink");
        }};
        childSkus.add(skuModel1);
        childSkus.add(skuModel2);
        childSkus.add(skuModel3);

        request.setChildSkus(childSkus);
        // ---------------------------

        controller.addProduct(request);

        assertTrue("Product not found in DB", productService.getProductsByName(newProductName).isPresent());
        assertTrue("Product does not have skus", productService.getProductsByName(newProductName).orElse(new Product()).getChildSkus().size() == 3);
    }

    @Test
    @Transactional
    public void productCatalogueController_updateProductTest() {
        String productName = "Champions Watches";

        if (!productService.getProductsByName(productName).isPresent()) {
            System.out.println("product with name " + productName + " needed for test");
            return;
        }

        Product product = productService.getProductsByName(productName).get();

        ProductModel request = new ProductModel();

        Double oldPrice = product.getPrice();
        Double newPrice = oldPrice + 10;
        request.setPrice(newPrice);

        // sku model for creating sku
        List<Map<String, String>> childSkus = new ArrayList<>();
        Map<String, String> skuModel1 = new HashMap<String, String>(){{
                put("color", "white testing");
        }};
        // sku model for updating existing sku
        ElectronicDeviceSku sku = (ElectronicDeviceSku) product.getChildSkus().get(0);
        String newColor = "pink testing";
        Map<String, String> skuModel2 = new HashMap<String, String>(){{
            put("id", sku.getId().toString());
            put("color", newColor);
        }};

        childSkus.add(skuModel1);
        childSkus.add(skuModel2);
        request.setChildSkus(childSkus);

        controller.updateProduct(product.getId(), request);

        Product updatedProduct = productService.getById(product.getId());
        String colorOfUpdatedSku = updatedProduct.getChildSkus().stream()
                .filter(skuFromDb -> skuFromDb.getId().equals(sku.getId()))
                .findFirst()
                .map(skuFromDb -> (ElectronicDeviceSku) skuFromDb)
                .get()
                .getColor();

        assertTrue("Child sku not updated", colorOfUpdatedSku.equals(newColor));
        assertTrue("Product price not updated", newPrice.equals(product.getPrice()));
    }
}
