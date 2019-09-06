package com.gridu.microservice.shoppingcart.rest.service;

import com.gridu.microservice.shoppingcart.data.model.Address;
import com.gridu.microservice.shoppingcart.data.model.CommerceItem;
import com.gridu.microservice.shoppingcart.data.model.ShoppingCart;
import com.gridu.microservice.shoppingcart.rest.model.CommerceItemRequest;
import com.gridu.microservice.shoppingcart.rest.model.Price;
import com.gridu.microservice.shoppingcart.rest.model.ProductResponse;
import com.gridu.microservice.shoppingcart.rest.model.TaxesCalculationOrder;
import com.gridu.microservice.shoppingcart.rest.model.TaxesCalculationRequest;
import com.gridu.microservice.shoppingcart.rest.model.TaxesCalculationResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class ShoppingCartService {

	private static AtomicInteger commerceItemCount = new AtomicInteger(0);

	@Autowired
	private WebClient.Builder productCatalogWebClient;

	@Autowired
	private WebClient.Builder taxesCalculationWebClient;

	public String generateNextCommerceItemId() {
		return "ci"+commerceItemCount.incrementAndGet();
	}

	public ShoppingCart addCommerceItem(ShoppingCart shoppingCart, CommerceItemRequest commerceItemRequest) {

		ProductResponse product = productCatalogWebClient

			.build()
			.get()
			.uri("/catalog/search/products?skuId={skuId}", commerceItemRequest.getSkuId())
			.retrieve()
			.bodyToMono(new ParameterizedTypeReference<ProductResponse>(){})
			.block();


		if (product != null) {
			CommerceItem commerceItem = new CommerceItem();
			commerceItem.setId(generateNextCommerceItemId());
			commerceItem.setProductId(product.getId());
			commerceItem.setSkuId(commerceItemRequest.getSkuId());
			commerceItem.setQuantity(commerceItemRequest.getQuantity());
			commerceItem.setPrice(product.getPrice());
			commerceItem.setCategory(product.getCategory());

			shoppingCart.addCommerceItem(commerceItem);
		}

		calculatePrices(shoppingCart);

		return shoppingCart;
	}


	public ShoppingCart deleteCommerceItem(ShoppingCart shoppingCart, CommerceItem commerceItem) {

		boolean deleted = shoppingCart.getCommerceItems().remove(commerceItem);
		if (deleted) {
			calculatePrices(shoppingCart);
		}
		return shoppingCart;
	}

	public ShoppingCart applyShippingAddress(ShoppingCart shoppingCart, Address address) {
		shoppingCart.setShippingAddress(address);

		calculatePrices(shoppingCart);

		return shoppingCart;
	}

	protected void calculatePrices(ShoppingCart shoppingCart) {
		double commerceItemsTotal = 0d;

		for(CommerceItem commerceItem : shoppingCart.getCommerceItems()) {
			double totalPrice = commerceItem.getPrice() * commerceItem.getQuantity();
			totalPrice = Price.roundPrice(totalPrice);
			commerceItem.setTotalPrice(totalPrice);
			commerceItemsTotal += totalPrice;
		}

		if (!shoppingCart.getCommerceItems().isEmpty() && shoppingCart.getShippingAddress() != null && shoppingCart.getShippingAddress().getState() != null) {
			calculateTaxes(shoppingCart);
		}

		shoppingCart.getPriceInfo().setCommerceItemTotal(commerceItemsTotal);
		double orderTotal = shoppingCart.getPriceInfo().getCommerceItemTotal() + shoppingCart.getPriceInfo().getTaxesTotal();
		shoppingCart.getPriceInfo().setTotal(orderTotal);
	}

	protected void calculateTaxes(ShoppingCart shoppingCart) {
		double taxesTotal = 0d;

		TaxesCalculationRequest taxesCalculationRequest = new TaxesCalculationRequest();
		taxesCalculationRequest.getAddress().setState(shoppingCart.getShippingAddress().getState());

		for(CommerceItem commerceItem : shoppingCart.getCommerceItems()) {
			TaxesCalculationRequest.TaxCalculationItem taxCalculationItem = new TaxesCalculationRequest.TaxCalculationItem();
			taxCalculationItem.setId(commerceItem.getId());
			taxCalculationItem.setCategory(commerceItem.getCategory());
			taxCalculationItem.setPrice(commerceItem.getPrice());
			taxesCalculationRequest.getItems().add(taxCalculationItem);
		}

		Mono<TaxesCalculationRequest> taxesCalculationRequestMono = Mono.just(taxesCalculationRequest);

		TaxesCalculationResponse taxesCalculationResponse = taxesCalculationWebClient
			.build()
			.post()
			.uri("/taxes/calculation/v2")
			.body(taxesCalculationRequestMono, TaxesCalculationRequest.class)
			.retrieve()
			.bodyToMono(new ParameterizedTypeReference<TaxesCalculationResponse>(){})
			.block();

		for(CommerceItem commerceItem : shoppingCart.getCommerceItems()) {
			TaxesCalculationOrder.TaxCalculationItem taxCalculationItem = taxesCalculationResponse.getOrder().getItems().stream()
				.filter(item -> commerceItem.getId().equals(item.getId()))
				.findFirst()
				.orElseGet(() -> null);

			commerceItem.getTaxes().setStateTax(taxCalculationItem.getTaxes().getStateTax());

			taxesTotal += commerceItem.getTaxes().getStateTax();
		}

		taxesTotal = Price.roundPrice(taxesTotal);

		shoppingCart.getPriceInfo().setTaxesTotal(taxesTotal);
	}

}
