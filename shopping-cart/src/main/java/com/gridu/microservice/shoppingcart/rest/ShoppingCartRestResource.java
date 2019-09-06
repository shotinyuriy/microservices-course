package com.gridu.microservice.shoppingcart.rest;

import com.gridu.microservice.rest.validation.ErrorResponse;
import com.gridu.microservice.rest.validation.ErrorResponseTransformer;
import com.gridu.microservice.rest.validation.ValidationResult;
import com.gridu.microservice.shoppingcart.data.model.Address;
import com.gridu.microservice.shoppingcart.data.model.CommerceItem;
import com.gridu.microservice.shoppingcart.data.model.ShoppingCart;
import com.gridu.microservice.shoppingcart.rest.model.CommerceItemRequest;
import com.gridu.microservice.shoppingcart.rest.model.ShoppingCartSessionContainer;
import com.gridu.microservice.shoppingcart.rest.service.ShoppingCartService;
import com.gridu.microservice.shoppingcart.rest.validation.ValidatorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.ValidatorFactory;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Set;

@RestController
@RequestMapping("/carts")
public class ShoppingCartRestResource {

	@Resource(name="shoppingCart")
	private ShoppingCartSessionContainer shoppingCartSessionContainer;

	@Autowired
	private ValidatorService validatorService;

	@Autowired
	private ErrorResponseTransformer errorResponseTransformer;

	@Autowired
	private ShoppingCartService shoppingCartService;

	@GetMapping("/current")
	public ResponseEntity<Object> getShoppingCart() {
		return ResponseEntity.ok(shoppingCartSessionContainer.getShoppingCart());
	}

	@PostMapping("/current/commerceItems")
	public ResponseEntity<Object> addCommerceItem(@RequestBody CommerceItemRequest commerceItemRequest) {
		Set<ValidationResult> validationResults = validatorService.validate(commerceItemRequest);
		if (!validationResults.isEmpty()) {
			Set<ErrorResponse> errorResponses = errorResponseTransformer.fromValidationResults(validationResults);
			return ResponseEntity.badRequest().body(errorResponses);
		}
		ShoppingCart shoppingCart = shoppingCartSessionContainer.getShoppingCart();
//		synchronized (shoppingCart) {
			shoppingCart = shoppingCartService.addCommerceItem(shoppingCart, commerceItemRequest);
//		}
		URI uri = null;
		try {
			uri = new URI("/carts/current/commerceItems");
		} catch (URISyntaxException e) {
			e.printStackTrace();
			return ResponseEntity.noContent().build();
		}
		return ResponseEntity.created(uri).body(shoppingCart);
	}

	@DeleteMapping("/current/commerceItems/{commerceItemId}")
	public ResponseEntity<Object> deleteCommerceItem(@PathVariable String commerceItemId) {
		ShoppingCart shoppingCart = shoppingCartSessionContainer.getShoppingCart();
//		synchronized (shoppingCart) {
			CommerceItem commerceItem = shoppingCart.findCommerceItem(commerceItemId);
			if (commerceItem == null) {
				return ResponseEntity.notFound().build();
			}
			shoppingCart = shoppingCartService.deleteCommerceItem(shoppingCart, commerceItem);
			return ResponseEntity.noContent().build();
//		}
	}


	@PostMapping("/current/shippingAddress")
	public ResponseEntity<Object> addShippingAddress(@RequestBody Address address) {
		Set<ValidationResult> validationResults = validatorService.validate(address);
		if (!validationResults.isEmpty()) {
			Set<ErrorResponse> errorResponses = errorResponseTransformer.fromValidationResults(validationResults);
			return ResponseEntity.badRequest().body(errorResponses);
		}
		ShoppingCart shoppingCart = shoppingCartSessionContainer.getShoppingCart();
//		synchronized (shoppingCart) {
			shoppingCart = shoppingCartService.applyShippingAddress(shoppingCart, address);
//		}
		URI uri = null;
		try {
			uri = new URI("/carts/current/shippingAddress");
		} catch (URISyntaxException e) {
			e.printStackTrace();
			return ResponseEntity.noContent().build();
		}
		return ResponseEntity.created(uri).body(shoppingCart);
	}
}
