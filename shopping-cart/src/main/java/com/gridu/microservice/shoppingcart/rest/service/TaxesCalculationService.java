package com.gridu.microservice.shoppingcart.rest.service;

import com.gridu.microservice.shoppingcart.rest.model.TaxesCalculationRequest;
import com.gridu.microservice.shoppingcart.rest.model.TaxesCalculationResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class TaxesCalculationService {

	@Autowired
	private WebClient.Builder taxesCalculationWebClient;

	public TaxesCalculationResponse calculateTaxes(TaxesCalculationRequest taxesCalculationRequest) {
		Mono<TaxesCalculationRequest> taxesCalculationRequestMono = Mono.just(taxesCalculationRequest);

		TaxesCalculationResponse taxesCalculationResponse = taxesCalculationWebClient
			.build()
			.post()
			.uri("/taxes/calculation/v2")
			.body(taxesCalculationRequestMono, TaxesCalculationRequest.class)
			.retrieve()
			.bodyToMono(new ParameterizedTypeReference<TaxesCalculationResponse>(){})
			.block();

		return taxesCalculationResponse;
	}
}
