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

	public Product fromProductRequest(ProductRequest productRequest) {
		Product product = new Product();
		product.setName(productRequest.getName());
		product.setCategory(productRequest.getCategory());
		product.setPrice(productRequest.getPrice());

		List<Sku> childSkus = new ArrayList<>();
		productRequest.getChildSkus().forEach(skuRequest -> {
			childSkus.add(skuTransformer.fromSkuRequest(skuRequest));
		});
		product.setChildSkus(childSkus);
		return product;
	}
}
