package com.gridu.microservices.productcatalog.rest;

import com.gridu.microservices.productcatalog.exception.CustomConstraintViolationException;
import com.gridu.microservices.productcatalog.model.*;
import com.gridu.microservices.productcatalog.rest.model.ProductId;
import com.gridu.microservices.productcatalog.rest.model.ProductModel;
import com.gridu.microservices.productcatalog.rest.model.SkuId;
import com.gridu.microservices.productcatalog.rest.transformer.ProductTransformer;
import com.gridu.microservices.productcatalog.rest.transformer.ValidationResultTransformer;
import com.gridu.microservices.productcatalog.service.ProductCategoryService;
import com.gridu.microservices.productcatalog.service.ProductService;
import com.gridu.microservices.productcatalog.service.SkuService;
import com.gridu.microservices.productcatalog.validation.ValidationResult;
import com.gridu.microservices.productcatalog.validation.ValidationService;
import com.gridu.microservices.productcatalog.validation.group.CategoryShouldExist;
import com.gridu.microservices.productcatalog.validation.group.ProductNameShouldNotExist;
import com.gridu.microservices.productcatalog.validation.group.SkuCannotBeEmpty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.groups.Default;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by anasimijonovic on 8/19/19.
 */
@RestController
@RequestMapping("/catalogue")
public class ProductCatalogueController {

    @Autowired
    private ProductService productService;

    @Autowired
    private ProductCategoryService productCategoryService;

    @Autowired
    private SkuService skuService;

    @Autowired
    private ProductTransformer productTransformer;

    @Autowired
    private ValidationService validationService;

    @Autowired
    private ValidationResultTransformer validationResultTransformer;

    @GetMapping(value = "/products", produces = "application/json")
    public ResponseEntity<List<ProductModel>> getAllProducts() {
        return ResponseEntity.ok(productTransformer.toProductModels(productService.getAllProducts()));
    }

    @GetMapping(value = "/products/{productId}", produces = "application/json")
    public ResponseEntity<?> getProduct(@PathVariable Long productId) {
        ProductId productIdHolder = new ProductId(productId);

        List<ValidationResult> validationResults = validationService.validate(productIdHolder);
        if (!validationResults.isEmpty()) {
            throw new CustomConstraintViolationException("Constraint violation.",
                    validationResultTransformer.provideValidationErrorResponse(validationResults),
                    HttpStatus.NOT_FOUND);
        }

        Product product = productService.getById(productId);

        return ResponseEntity.ok(productTransformer.toProductModel(product));
    }

    @PostMapping(value = "/products", produces = "application/json")
    public ResponseEntity<?> addProduct(@RequestBody ProductModel productModel) {
        // validate default and existence of category and uniqueness of product name
        // and sku existence
        List<ValidationResult> requestDataValidationResults = validationService.validate(productModel,
                Default.class, CategoryShouldExist.class, ProductNameShouldNotExist.class, SkuCannotBeEmpty.class);

        if (!requestDataValidationResults.isEmpty()) {
            throw new CustomConstraintViolationException("Constraint violation.",
                    validationResultTransformer.provideValidationErrorResponse(requestDataValidationResults),
                    HttpStatus.BAD_REQUEST);
        }

        Product product = fromProductModel(productModel);

        List<ValidationResult> validationResults = validationService.validate(product, Default.class);
        if (!validationResults.isEmpty()) {
            throw new CustomConstraintViolationException("Constraint violation.",
                    validationResultTransformer.provideValidationErrorResponse(validationResults),
                    HttpStatus.BAD_REQUEST);
        }

        Product savedProduct = productService.addProduct(product);

        createChildSkus(savedProduct, productModel);

        return ResponseEntity.status(HttpStatus.CREATED).contentType(MediaType.APPLICATION_JSON)
                .body("/catalog/products/" + savedProduct.getId());
    }

    @PatchMapping(value = "/products/{productId}", produces = "application/json")
    public ResponseEntity<?> updateProduct(@PathVariable Long productId, @RequestBody ProductModel productModel) {
        // first validate existence of product id
        ProductId productIdHolder = new ProductId(productId);

        List<ValidationResult> validationResultsProductId = validationService.validate(productIdHolder);

        // validate product name uniqueness
        List<ValidationResult> validationResults = productModel.getName() == null ? new ArrayList<>() : validationService.validate(productModel, ProductNameShouldNotExist.class);
        // using merged list of validation results
        validationResults.addAll(validationResultsProductId);

        if (!validationResults.isEmpty()) {
            throw new CustomConstraintViolationException("Constraint violation.",
                    validationResultTransformer.provideValidationErrorResponse(validationResults),
                    HttpStatus.NOT_FOUND);
        }

        Product productFromDb = productService.getById(productId);

        patchProduct(productFromDb, productModel);

        return ResponseEntity.status(HttpStatus.OK).body("OK");
    }

    @DeleteMapping(value = "/products/{productId}", produces = "application/json")
    public ResponseEntity<?> deleteProduct(@PathVariable Long productId) {
        ProductId productIdHolder = new ProductId(productId);

        List<ValidationResult> validationResults = validationService.validate(productIdHolder);
        if (!validationResults.isEmpty()) {
            throw new CustomConstraintViolationException("Constraint violation.",
                    validationResultTransformer.provideValidationErrorResponse(validationResults),
                    HttpStatus.NOT_FOUND);
        }

        productService.deleteProduct(productId);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("OK");
    }

    @GetMapping(value = "/search/products", produces = "application/json")
    public ResponseEntity<?> getSku(@RequestParam Long skuId) {
        SkuId skuIdHolder = new SkuId(skuId);

        List<ValidationResult> validationResults = validationService.validate(skuIdHolder);
        if (!validationResults.isEmpty()) {
            throw new CustomConstraintViolationException("Constraint violation.",
                    validationResultTransformer.provideValidationErrorResponse(validationResults),
                    HttpStatus.NOT_FOUND);
        }

        Sku sku = skuService.getSkuById(skuId);

        return ResponseEntity.ok(sku);
    }

    public Product fromProductModel(ProductModel model) {
        Product product = new Product();
        product.setName(model.getName());
        product.setPrice(model.getPrice());
        product.setCategory(productCategoryService.getProductCategoriesByName(model.getCategory()).get());

        return product;
    }

    private void createChildSkus(Product product, ProductModel model) {
        if (model.getChildSkus() == null || model.getChildSkus().size() == 0) {
            skuService.saveSku(new Sku(product));

        } else {
            String categoryName = product.getCategory().getName();

            // reflection?
            if (categoryName.equals("electronic device")) {
                for (Map<String, String> sku : model.getChildSkus()) {
                    ElectronicDeviceSku electronicDeviceSku = new ElectronicDeviceSku();
                    electronicDeviceSku.setColor(sku.get("color"));

                    electronicDeviceSku.setProduct(product);

                    skuService.saveSku(electronicDeviceSku);
                }

            } else if (categoryName.equals("clothing")) {
                for (Map<String, String> sku : model.getChildSkus()) {
                    ClothingSku clothingSku = new ClothingSku();
                    clothingSku.setSize(sku.get("size"));

                    clothingSku.setProduct(product);

                    skuService.saveSku(clothingSku);
                }
            }
        }
    }

    private void patchProduct(Product product, ProductModel model) {
        if (model.getName() != null) {
            product.setName(model.getName());
        }

        if (model.getPrice() != null) {
            product.setPrice(model.getPrice());
        }

        String categoryName = product.getCategory().getName();

        if (model.getChildSkus() != null && model.getChildSkus().size() > 0) {
            if (categoryName.equals("electronic device")) {
                for (Map<String, String> sku : model.getChildSkus()) {
                    ElectronicDeviceSku electronicDeviceSku;

                    if (sku.containsKey("id")) {
                        electronicDeviceSku = (ElectronicDeviceSku) skuService.getSkuById(Long.valueOf(sku.get("id")));
                        electronicDeviceSku.setColor(sku.get("color"));

                    } else {
                        electronicDeviceSku = new ElectronicDeviceSku();
                        electronicDeviceSku.setColor(sku.get("color"));
                        electronicDeviceSku.setProduct(product);
                    }

                    skuService.saveSku(electronicDeviceSku);
                }


            } else if (categoryName.equals("clothing")) {
                for (Map<String, String> sku : model.getChildSkus()) {
                    ClothingSku clothingSku;

                    if (sku.containsKey("id")) {
                        clothingSku = (ClothingSku) skuService.getSkuById(Long.valueOf(sku.get("id")));
                        clothingSku.setSize(sku.get("size"));

                    } else {
                        clothingSku = new ClothingSku();
                        clothingSku.setSize(sku.get("size"));
                        clothingSku.setProduct(product);
                    }

                    skuService.saveSku(clothingSku);
                }
            }
        }

        productService.addProduct(product);
    }

    @GetMapping("/createCategories")
    public void createCategories() {
        ProductCategory category = new ProductCategory();
        category.setName("clothing");
        productCategoryService.addProductCategory(category);

        ProductCategory category1 = new ProductCategory();
        category1.setName("books");
        productCategoryService.addProductCategory(category1);

        ProductCategory category2 = new ProductCategory();
        category2.setName("electronic device");
        productCategoryService.addProductCategory(category2);
    }
}
