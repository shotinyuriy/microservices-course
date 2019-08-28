package com.gridu.microservices.product.catalog.transformer;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.gridu.microservices.product.catalog.model.Product;
import com.gridu.microservices.product.catalog.model.ProductSku;
import com.gridu.microservices.product.catalog.rest.model.ProductRestModel;
import com.gridu.microservices.product.catalog.rest.model.ProductRestModel.ProductRestModelBuilder;
import com.gridu.microservices.product.catalog.rest.model.ProductRestModel.SkuModel;
import com.gridu.microservices.product.catalog.service.CurrencyService;
import com.gridu.microservices.product.catalog.service.ProductCategoryService;
import com.gridu.microservices.product.catalog.service.ProductService;
import com.gridu.microservices.product.catalog.service.SkuTypeService;

@Component
public class ProductTransformer {

	@Autowired
	private CurrencyService currencyService;

	@Autowired
	private ProductCategoryService productCategoryService;

	@Autowired
	private ProductService productService;

	@Autowired
	private SkuTypeService skuTypeService;

	public Product createProduct(ProductRestModel model) {
		Product product = new Product();
		product.setCategory(getProductCategoryService().findByName(model.getCategory()));
		product.setCurrency(getCurrencyService().findByValue(model.getCurrency()));
		product.setName(model.getName());
		product.setPrice(model.getPrice());
		populateProductSkus(model, product);
		return product;
	}

	public ProductRestModel createProductRestModel(Product product, boolean includeProductSkus) {
		return new ProductRestModelBuilder().setId(product.getId()).setName(product.getName())
				.setPrice(product.getPrice()).setCategory(product.getCategory().getName())
				.setCurrency(product.getCurrency().getValue())
				.setProductSkus(extractProductSkus(includeProductSkus ? product.getChildSkus() : null)).build();
	}

	private ProductSku createProductSku(Product product, SkuModel sku) {
		ProductSku productSku = new ProductSku();
		productSku.setSkuType(getSkuTypeService().findByValue(sku.getSkus().keySet().iterator().next()));
		productSku.setSkuValue(sku.getSkus().values().iterator().next());
		return productSku;
	}

	private List<SkuModel> extractProductSkus(Collection<ProductSku> skus) {
		if (skus == null)
			return null;

		List<SkuModel> skusResponse = new ArrayList<SkuModel>();
		for (ProductSku productSku : skus) {
			SkuModel sku = new SkuModel();
			sku.setId(productSku.getId());
			sku.setSkus(Collections.singletonMap(productSku.getSkuType().getValue(), productSku.getSkuValue()));
			skusResponse.add(sku);
		}
		return skusResponse;
	}

	private CurrencyService getCurrencyService() {
		return currencyService;
	}

	private ProductCategoryService getProductCategoryService() {
		return productCategoryService;
	}

	private ProductService getProductService() {
		return productService;
	}

	private SkuTypeService getSkuTypeService() {
		return skuTypeService;
	}

	private void populateProductSkus(ProductRestModel model, Product product) {
		if (model.getSkus() == null || model.getSkus().isEmpty()) {
			ProductSku defaultSkuType = getProductService().getDefaultSkuType(product);
			if (defaultSkuType != null) {
				product.addChildSkus(defaultSkuType);
			}
		} else {
			for (SkuModel sku : model.getSkus()) {
				product.addChildSkus(createProductSku(product, sku));
			}
		}
	}
}
