package com.gridu.microservice.shoppingcart.rest.service;

import com.gridu.microservice.shoppingcart.ProductRedisTemplate;
import com.gridu.microservice.shoppingcart.rest.model.ProductResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

@Repository
public class ProductCatalogCache {

	@Value("${shoppingCart.productCatalog.cache.expirationTime}")
	private long cacheExpirationTime;

	private Logger LOGGER = LoggerFactory.getLogger(ProductCatalogCache.class);

	public static final String PRODUCT_KEY = "productBySku";

	@Resource(name="productRedisTemplate")
	private HashOperations<String, String, ProductResponse> hashOperations;

	@ProductRedisTemplate
	private RedisTemplate<String, ProductResponse> redisTemplate;

	public ProductResponse findProductBySkuId(String skuId) {
		LOGGER.info("trying to find product by skuId skuId="+skuId);
		return hashOperations.get(PRODUCT_KEY+":"+skuId, "_");
	}

	public void putProductBySkuId(String skuId, ProductResponse productResponse) {

		LOGGER.info("putting product by skuId skuId="+skuId+" productResponse="+productResponse);
		hashOperations.put(PRODUCT_KEY+":"+skuId, "_", productResponse);
		redisTemplate.expire(PRODUCT_KEY+":"+skuId, cacheExpirationTime, TimeUnit.MILLISECONDS);
	}

	public void evictProductBySkuId(String skuId) {
		hashOperations.delete(PRODUCT_KEY+":"+skuId, "_");
	}
}
