package com.gridu.microservices.product.catalog.scheduler;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

import com.gridu.microservices.product.catalog.model.ProductCategory;
import com.gridu.microservices.product.catalog.service.ProductCategoryService;

import reactor.core.publisher.Mono;

import org.slf4j.Logger;

@Service
public class SchedulerTasks {

	private final Logger logger = LoggerFactory.getLogger(SchedulerTasks.class);

	@Autowired
	private ProductCategoryService productCategoryService;

	@Autowired
	private RestTemplate restTemplate;

	@Autowired
	private WebClient.Builder webClientBuilder;

	@Scheduled(fixedDelay = 5000)
	public void getTaxesCategoriesRestTemplate() {
		List<String> categories = getTaxCategoriesFromRemoteSync();
		updateCurrentProductCategories(categories);
	}

	//@Scheduled(fixedDelay = 10000)
	public void getTaxesCategoriesWebClient() {
		getTaxCategoriesFromRemoteAsync();
	}

	private Logger getLogger() {
		return logger;
	}

	private ProductCategoryService getProductCategoryService() {
		return productCategoryService;
	}

	private RestTemplate getRestTemplate() {
		return restTemplate;
	}

	private void getTaxCategoriesFromRemoteAsync() {
		Mono<List> bodyToMono = getWebClientBuilder().build().get().uri("http://localhost:8090/taxes/taxCategories/v1")
				.retrieve().bodyToMono(List.class); // .block(); - makes this call synchronous
		bodyToMono.subscribe(categoryList -> updateCurrentProductCategories(categoryList));
	}

	private List<String> getTaxCategoriesFromRemoteSync() {
		boolean processFurtherRequest = true;
		RequestEntity<?> request = null;
		List<String> categories = new ArrayList<String>();
		try {
			request = RequestEntity.get(new URI("http://taxes-calculation-service/taxes/taxCategories/v1"))
					.accept(MediaType.APPLICATION_JSON).build();
		} catch (URISyntaxException e) {
			processFurtherRequest = false;
			getLogger().error("Requested url is not valid. Can not access to the tax-category service");
		}
		if (processFurtherRequest) {
			try {
				ResponseEntity<List<String>> response = getRestTemplate().exchange(request,
						new ParameterizedTypeReference<List<String>>() {
						});
				categories = response.getBody();
			} catch (Exception e) {
				getLogger().error("Tax-category service request not completed.");
			}

		}
		System.err.println("Done with tax-calc remote call");
		return categories;
	}

	private WebClient.Builder getWebClientBuilder() {
		return webClientBuilder;
	}

	private void updateCurrentProductCategories(List<String> categories) {
		for (String category : categories) {
			ProductCategory categoryByName = getProductCategoryService().findByName(category);
			if (categoryByName == null) {
				ProductCategory pc = new ProductCategory();
				pc.setName(category);
				getProductCategoryService().save(pc);
			}
		}
	}
}
