package com.gridu.microservices.productcatalog.rest.transformer;

import com.gridu.microservices.productcatalog.model.ClothingSku;
import com.gridu.microservices.productcatalog.model.ElectronicDeviceSku;
import com.gridu.microservices.productcatalog.model.Product;
import com.gridu.microservices.productcatalog.model.Sku;
import com.gridu.microservices.productcatalog.rest.model.ProductModel;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by anasimijonovic on 8/21/19.
 */
@Component
public class ProductTransformer {
    public List<ProductModel> toProductModels(List<Product> products) {
        List<ProductModel> viewModels = new ArrayList<>();
        for (Product product : products) {
            viewModels.add(toProductModel(product));
        }

        return viewModels;
    }

    public ProductModel toProductModel(Product product) {
        ProductModel viewModel = new ProductModel();
        viewModel.setId(product.getId());
        viewModel.setName(product.getName());
        viewModel.setCategory(product.getCategory().getName());
        viewModel.setPrice(product.getPrice());
        viewModel.setChildSkus(getChildSkus(product.getChildSkus()));

        return viewModel;
    }

    public List<Map<String, String>> getChildSkus(List<Sku> childSkus) {
        List<Map<String, String>> result = new ArrayList<>();

        for (Sku childSku : childSkus) {
            Map<String, String> skuModels = new HashMap<>();

            if (childSku instanceof ClothingSku) {
                skuModels.put("size", ((ClothingSku) childSku).getSize());
                result.add(skuModels);

            } else if (childSku instanceof ElectronicDeviceSku) {
                skuModels.put("color", ((ElectronicDeviceSku) childSku).getColor());
                result.add(skuModels);
            }
        }

        return result;
    }

}
