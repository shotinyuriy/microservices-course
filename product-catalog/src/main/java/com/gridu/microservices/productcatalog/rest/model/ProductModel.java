package com.gridu.microservices.productcatalog.rest.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.gridu.microservices.productcatalog.validation.annotation.ExistingCategory;
import com.gridu.microservices.productcatalog.validation.annotation.SkuNotEmpty;
import com.gridu.microservices.productcatalog.validation.annotation.UniqueProductName;
import com.gridu.microservices.productcatalog.validation.group.CategoryShouldExist;
import com.gridu.microservices.productcatalog.validation.group.ProductNameShouldNotExist;
import com.gridu.microservices.productcatalog.validation.group.SkuCannotBeEmpty;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Map;

/**
 * Created by anasimijonovic on 8/21/19.
 */
@SkuNotEmpty(groups = SkuCannotBeEmpty.class)
public class ProductModel {
    private Long id;

    @NotEmpty
    @UniqueProductName(groups = ProductNameShouldNotExist.class)
    private String name;

    @NotEmpty(groups = CategoryShouldExist.class)
    @ExistingCategory(groups = CategoryShouldExist.class)
    private String category;

    @Min(0)
    @NotNull
    private Double price;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<Map<String, String>> childSkus;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public List<Map<String, String>> getChildSkus() {
        return childSkus;
    }

    public void setChildSkus(List<Map<String, String>> childSkus) {
        this.childSkus = childSkus;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }
}
