package com.gridu.microservice.shoppingcart.rest.service;

import com.gridu.microservice.shoppingcart.rest.model.TaxesCalculationOrder;
import com.gridu.microservice.shoppingcart.rest.model.TaxesCalculationRequest;
import com.gridu.microservice.shoppingcart.rest.model.TaxesCalculationResponse;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.ribbon.proxy.annotation.Hystrix;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class TaxesCalculationService {

	@Autowired
	private WebClient.Builder taxesCalculationWebClient;

	@Hystrix
	@HystrixCommand(fallbackMethod = "emptyResponse")
	public TaxesCalculationResponse calculateTaxes(TaxesCalculationRequest taxesCalculationRequest) {

		Mono<TaxesCalculationRequest> taxesCalculationRequestMono = Mono.just(taxesCalculationRequest);

		TaxesCalculationResponse taxesCalculationResponse = taxesCalculationWebClient
			.build()
			.post()
			.uri("/taxes/calculation/v2")
			.body(taxesCalculationRequestMono, TaxesCalculationRequest.class)
			.retrieve()
			.bodyToMono(new ParameterizedTypeReference<TaxesCalculationResponse>() {
			})
			.block();

		return taxesCalculationResponse;
	}

	/**
	 * "message": "fallback method wasn't found:
	 * emptyResponse([class com.gridu.microservice.shoppingcart.rest.model.TaxesCalculationRequest])"
	 *
	 * @param taxesCalculationRequest
	 * @return
	 */
	public TaxesCalculationResponse emptyResponse(TaxesCalculationRequest taxesCalculationRequest) {
		TaxesCalculationResponse taxesCalculationResponse = new TaxesCalculationResponse();
		TaxesCalculationOrder order = new TaxesCalculationOrder();
		taxesCalculationResponse.setOrder(order);
		return taxesCalculationResponse;
	}
}
