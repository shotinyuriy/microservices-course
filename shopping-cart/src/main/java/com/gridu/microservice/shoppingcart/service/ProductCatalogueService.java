package com.gridu.microservice.shoppingcart.service;

import com.gridu.microservice.shoppingcart.model.ProductInfoModel;
import com.gridu.microservice.shoppingcart.model.SkuModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;


/**
 * Created by anasimijonovic on 8/30/19.
 */
@Service
public class ProductCatalogueService {

    @Autowired
    private WebClient.Builder webClient;

    @Value(value = "${catalogue.service.baseUrl}")
    private String baseUrl;

    public ProductInfoModel getProductInfo(Long skuId) {
        String path = "/catalogue/search/products/";

        SkuModel skuModel = webClient.baseUrl(baseUrl).build().get()
                .uri(uriBuilder -> uriBuilder
                        .path(path)
                        .queryParam("skuId", skuId)
                        .build())
                .retrieve()
                .bodyToMono(SkuModel.class)
                .block();

        ProductInfoModel productInfoModel = new ProductInfoModel();
        productInfoModel.setPrice(skuModel.getProductModel().getPrice());
        productInfoModel.setId(skuModel.getProductModel().getId());
        productInfoModel.setCategory(skuModel.getProductModel().getCategory());

        return productInfoModel;
    }
}
