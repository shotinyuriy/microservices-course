package com.gridu.microservices.productcatalog.data.service;

import com.gridu.microservices.productcatalog.data.model.ProductCategory;
import com.gridu.microservices.productcatalog.data.model.TaxCategory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentSkipListSet;

@Service
public class ProductCategoryService {

	@Autowired
	private RestTemplate restTemplate;

	private static Logger LOGGER = LoggerFactory.getLogger(ProductCategoryService.class);

	private final ConcurrentSkipListSet<String> categories = new ConcurrentSkipListSet<String>();

	public List<String> getCategories() {
		if (categories.isEmpty()) {
			refreshCategories();
		}
		return new ArrayList<String>(categories);
	}

	@Scheduled(initialDelay = 0, fixedDelay = 60000)
	public void refreshCategories() {
		try {
			LOGGER.info("START REFRESHING CATEGORIES categories = " + categories);

			ResponseEntity<List<TaxCategory>> responseEntity = restTemplate.exchange(
				"http://taxes-calculation/taxes/categories/v1",
				HttpMethod.GET,
				null,
				new ParameterizedTypeReference<List<TaxCategory>>() {
				});

			List<TaxCategory> taxCategories = responseEntity.getBody();

			categories.clear();
			taxCategories.forEach(taxCategory -> categories.add(taxCategory.getName()));
			LOGGER.info("END REFRESHING CATEGORIES categories = " + categories);
		} catch (Exception e) {
			categories.clear();
			categories.add("books");
			categories.add(ProductCategory.CLOTHING);
			categories.add(ProductCategory.ELECTRONIC_DEVICES);
		}
	}
}
