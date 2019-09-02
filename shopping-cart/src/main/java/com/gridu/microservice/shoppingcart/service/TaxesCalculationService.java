package com.gridu.microservice.shoppingcart.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

/**
 * Created by anasimijonovic on 8/30/19.
 */
@Service
public class TaxesCalculationService {

    @Autowired
    private WebClient.Builder webClient;

    @Value(value = "${taxes.calculation.service.baseUrl}")
    private String baseUrl;

    public TaxesCalculationItemsModel getTaxesInfo(TaxesCalculationItemsModel request) {
        String path = "/taxes/calculation/v1/";

        return webClient.baseUrl(baseUrl).build().post()
                .uri(uriBuilder -> uriBuilder
                        .path(path)
                        .build())
                .body(BodyInserters.fromObject(request))
                .retrieve()
                .bodyToMono(TaxesCalculationItemsModel.class)
                .block();
    }
}
