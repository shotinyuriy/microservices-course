package com.gridu.microservices.productcatalog.rest;

import com.gridu.microservices.productcatalog.rest.model.ClothingSkuResponse;
import com.gridu.microservices.productcatalog.rest.model.SkuResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/catalog")
public class ProductCatalogRestResourceV1 {

	@GetMapping(value = "/")
	public ResponseEntity<List<SkuResponse>> getSkus() {

		return ResponseEntity.ok(generateFakeSkus("001"));
	}

	@GetMapping(value = "/products/{productId}/skus")
	public ResponseEntity<List<SkuResponse>> getSkusForProductId(@PathVariable String productId) {

		return ResponseEntity.ok(generateFakeSkus(productId));
	}

	private List<SkuResponse> generateFakeSkus(String productId) {
		List<SkuResponse> skus = new ArrayList<>();

		skus.add(new ClothingSkuResponse("sku1"+productId, "XS"));
		skus.add(new ClothingSkuResponse("sku2"+productId, "S"));
		skus.add(new ClothingSkuResponse("sku3"+productId, "M"));
		skus.add(new ClothingSkuResponse("sku4"+productId, "L"));
		skus.add(new ClothingSkuResponse("sku5"+productId, "XL"));
		skus.add(new ClothingSkuResponse("sku6"+productId, "XXL"));
		skus.add(new ClothingSkuResponse("sku7"+productId, "XXXL"));

		return  skus;
	}
}
