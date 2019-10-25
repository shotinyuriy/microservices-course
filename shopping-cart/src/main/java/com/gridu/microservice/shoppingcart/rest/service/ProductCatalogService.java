package com.gridu.microservice.shoppingcart.rest.service;

import com.gridu.microservice.shoppingcart.rest.model.ProductResponse;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.ribbon.proxy.annotation.Hystrix;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class ProductCatalogService {

	private Logger LOGGER = LoggerFactory.getLogger(ProductCatalogService.class);

	@Autowired
	private WebClient.Builder productCatalogWebClient;

	@Autowired
	private ProductCatalogCache productCatalogCache;

	@Value("${shoppingCart.productCatalog.cache.enabled}")
	private boolean enableProductCatalogCache;

	@HystrixCommand
	public ProductResponse findProductBySkuId(String skuId) {

		ProductResponse product = null;
		if (enableProductCatalogCache) {
			LOGGER.info("caching is enabled");
			product = productCatalogCache.findProductBySkuId(skuId);
			LOGGER.info("product="+product);
		} else {
			LOGGER.info("caching is disabled");
		}
		if (product == null) {
			LOGGER.info("calling product catalog microservice to find product by skuId="+skuId);
			product = productCatalogWebClient
				.build()
				.get()
				.uri("/catalog/search/products?skuId={skuId}", skuId)
				.retrieve()
				.bodyToMono(new ParameterizedTypeReference<ProductResponse>() {
				})
				.block();
			LOGGER.info("product="+product);
			if (enableProductCatalogCache) {
				productCatalogCache.putProductBySkuId(skuId, product);
			}
		}

		return product;
	}

	public ProductResponse emptyProductResponse(String skuId) {
		return null;
	}
}
