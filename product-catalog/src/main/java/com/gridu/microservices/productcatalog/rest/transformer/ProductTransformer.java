package com.gridu.microservices.productcatalog.rest.transformer;


import com.gridu.microservices.productcatalog.data.model.Product;
import com.gridu.microservices.productcatalog.data.model.Sku;
import com.gridu.microservices.productcatalog.rest.model.ProductRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ProductTransformer {

	@Autowired
	protected SkuTransformer skuTransformer;

	public Product fromProductRequest(ProductRequest productRequest, Product existingProduct) {
		if (existingProduct == null) {
			existingProduct = new Product();
		}
		final Product updatedProduct = existingProduct;
		updatedProduct.setName(productRequest.getName());
		if (productRequest.getCategory() != null) {
			updatedProduct.setCategory(productRequest.getCategory());
		}
		updatedProduct.setPrice(productRequest.getPrice());

		List<Sku> childSkus = new ArrayList<>();

		productRequest.getChildSkus().forEach(skuRequest -> {
			Sku sku = null;
			if (skuRequest.getId() != null) {
				sku = updatedProduct.findSku(skuRequest.getId());
			}
			childSkus.add(skuTransformer.fromSkuRequest(skuRequest, sku, updatedProduct.getCategory()));
		});
		updatedProduct.setChildSkus(childSkus);
		return updatedProduct;
	}
}
