package com.gridu.microservices.productcatalog.scheduler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by anasimijonovic on 8/23/19.
 */
@Service
public class SchedulerService {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private WebClient.Builder webClientBuilder;

    private Map<Long, TaxCategoryModel> model = new HashMap<>();

    @Scheduled(fixedDelayString = "${fixedDelay.in.milliseconds}")
    public void getTaxCategories() throws IOException {
        // generic type erasure -> should use parametrized type reference which uses reflection to capture class type
        String url = "http://localhost:8090/taxes/categories/v1";
        ResponseEntity<List<TaxCategoryModel>> response = restTemplate.exchange(url,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<TaxCategoryModel>>(){});

        List<TaxCategoryModel> taxCategoryModels = response.getBody();

        for (TaxCategoryModel taxCategoryModel : taxCategoryModels) {
            model.put(taxCategoryModel.getId(), taxCategoryModel);
        }
    }

    @Scheduled(fixedDelayString = "${fixedDelay.in.milliseconds}")
    public void getTaxCategoriesWebClient() throws IOException {
        String url = "http://localhost:8090/taxes/categories/v1";

        List<TaxCategoryModel> response = webClientBuilder
                .build()
                .get()
                .uri(url)
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<List<TaxCategoryModel>>(){})
                .block();

        for (TaxCategoryModel taxCategoryModel : response) {
            model.put(taxCategoryModel.getId(), taxCategoryModel);
        }
    }
}
