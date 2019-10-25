package com.gridu.microservice.shoppingcart;

import com.gridu.microservice.shoppingcart.rest.model.ProductResponse;
import com.gridu.microservice.shoppingcart.rest.model.SkuResponse;
import com.gridu.microservice.shoppingcart.rest.service.ProductCatalogCache;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductCatalogCacheTest {

	@Autowired
	private ProductCatalogCache productCatalogCache;

	@Test
	public void testProductCatalogPutAndFind() {
		String SKU_ID_1 = "skuId1";
		String SKU_ID_2 = "skuId2";

		ProductResponse productResponseBySkuId1 = new ProductResponse();
		productResponseBySkuId1.setCategory("category");
		productResponseBySkuId1.setChildSkus(new ArrayList<>());
		SkuResponse skuResponse = new SkuResponse();
		skuResponse.setId(SKU_ID_1);
		productResponseBySkuId1.getChildSkus().add(skuResponse);

		ProductResponse productResponseFound = productCatalogCache.findProductBySkuId(SKU_ID_1);
		Assert.assertNull(productResponseFound);

		productCatalogCache.putProductBySkuId(SKU_ID_1, productResponseBySkuId1);

		productResponseFound = productCatalogCache.findProductBySkuId(SKU_ID_1);
		Assert.assertNotNull(productResponseFound);

		Assert.assertEquals(productResponseBySkuId1.getCategory(), productResponseFound.getCategory());
		Assert.assertEquals(productResponseBySkuId1.getChildSkus().size(), productResponseFound.getChildSkus().size());

		ProductResponse productResponseFound2 = productCatalogCache.findProductBySkuId(SKU_ID_2);
		Assert.assertNull(productResponseFound2);

//		productCatalogCache.evictProductBySkuId(SKU_ID_1);
//
//		productResponseFound = productCatalogCache.findProductBySkuId(SKU_ID_1);
//		Assert.assertNull(productResponseFound);
	}
}
