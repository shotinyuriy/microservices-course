package com.gridu.microservice.shoppingcart.controller;

import com.gridu.microservice.shoppingcart.model.*;
import com.gridu.microservice.shoppingcart.service.ProductCatalogueService;
import com.gridu.microservice.shoppingcart.service.TaxesCalculationItemsModel;
import com.gridu.microservice.shoppingcart.service.TaxesCalculationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by anasimijonovic on 8/30/19.
 */
@Controller
@RequestMapping("/carts")
public class ShoppingCartController {

    @Autowired
    private ProductCatalogueService productCatalogueService;

    @Autowired
    private TaxesCalculationService taxesCalculationService;

    @Autowired
    private ShoppingCart shoppingCart;

    @GetMapping(value = "/current")
    @ResponseBody
    public ResponseEntity<ShoppingCartModel> getItemsFromCart() {
        return ResponseEntity.ok(toShoppingCartModel(shoppingCart));
    }

    @PostMapping(value = "/current/commerceItems")
    @ResponseBody
    public ResponseEntity<?> addItemToCart(@RequestBody ItemRequestModel requestModel) {
        ProductInfoModel productInfoModel = productCatalogueService.getProductInfo(requestModel.getSkuId());

        List<CommerceItem> commerceItems = shoppingCart.getCommerceItems();

        commerceItems.add(toCommerceItem(productInfoModel, requestModel));

        if (shoppingCart.hasShippingAddress()) {
            setTaxes(shoppingCart.getShippingAddress().getShippingAddress());
        }

        shoppingCart.calculateAndSetPriceInfo();

        return ResponseEntity.ok(toShoppingCartModel(shoppingCart));
    }

    @DeleteMapping(value = "/current/commerceItems/{id}")
    @ResponseBody
    public ResponseEntity<?> deleteItemFromCart(@PathVariable String id) {
        for (Iterator<CommerceItem> itemIterator = shoppingCart.getCommerceItems().listIterator(); itemIterator.hasNext(); ) {
            CommerceItem commerceItem = itemIterator.next();
            if (commerceItem.getId().equals(id)) {
                itemIterator.remove();
            }
        }

        shoppingCart.calculateAndSetPriceInfo();

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PostMapping(value = "/current/shippingAddress")
    @ResponseBody
    public ResponseEntity<ShoppingCartModel> addShippingAddress(@RequestBody ShippingAddressModel request) {
        shoppingCart.setShippingAddress(request);

        setTaxes(request.getShippingAddress());

        return ResponseEntity.ok(toShoppingCartModel(shoppingCart));
    }

    private void setTaxes(ShippingAddressModel.ShippingAddress shippingAddress) {
        // todo ana should be optimized to accept list of commerce items
        TaxesCalculationItemsModel requestModel = getTaxesRequestModel(shippingAddress);

        TaxesCalculationItemsModel taxesInfo = taxesCalculationService.getTaxesInfo(requestModel);

        setTaxesToShoppingCart(taxesInfo);

        shoppingCart.calculateAndSetPriceInfo();
    }

    private void setTaxesToShoppingCart(TaxesCalculationItemsModel taxesInfo) {
        for (CommerceItem cartItem : shoppingCart.getCommerceItems()) {
            cartItem.setTaxes(getTaxesModelForProduct(cartItem.getProductId(), taxesInfo).getTaxes());
        }
    }

    private TaxesCalculationItemsModel.TaxCalculationItemModel getTaxesModelForProduct(Long productId, TaxesCalculationItemsModel taxesModel) {
        List<TaxesCalculationItemsModel.TaxCalculationItemModel> models = taxesModel.getItems();

        return models.stream()
                .filter(model -> model.getId().equals(productId.toString()))
                .findFirst()
                .orElse(null);
    }

    private CommerceItem toCommerceItem(ProductInfoModel responseModel, ItemRequestModel requestModel) {
        CommerceItem commerceItem = new CommerceItem();
        commerceItem.setPrice(responseModel.getPrice());
        commerceItem.setSkuId(requestModel.getSkuId());
        commerceItem.setProductId(responseModel.getId());
        commerceItem.setQuantity(requestModel.getQuantity());
        commerceItem.calculateAndSetTotalPrice();

        return commerceItem;
    }

    private ShoppingCartModel toShoppingCartModel(ShoppingCart shoppingCart) {
        ShoppingCartModel model = new ShoppingCartModel();

        model.setId(shoppingCart.getId());
        model.setCommerceItems(shoppingCart.getCommerceItems());
        model.setPriceInfo(shoppingCart.getPriceInfo());
        model.setShippingAddress(shoppingCart.getShippingAddress() == null ? null : shoppingCart.getShippingAddress().getShippingAddress());

        return model;
    }

    private TaxesCalculationItemsModel getTaxesRequestModel(ShippingAddressModel.ShippingAddress shippingAddress) {
        TaxesCalculationItemsModel taxesModel = new TaxesCalculationItemsModel();
        taxesModel.setAddress(new TaxesCalculationItemsModel.ShippingAddress(shippingAddress.getState()));

        List<TaxesCalculationItemsModel.TaxCalculationItemModel> taxesItems = new ArrayList<>();

        for (CommerceItem commerceItem : shoppingCart.getCommerceItems()) {
            // it is needed for getting product category
            ProductInfoModel productInfoModel = productCatalogueService.getProductInfo(commerceItem.getSkuId());

            TaxesCalculationItemsModel.TaxCalculationItemModel model =
                    new TaxesCalculationItemsModel.TaxCalculationItemModel(
                            productInfoModel.getId().toString(),
                            productInfoModel.getCategory(),
                            commerceItem.getTotalPrice(),
                            new TaxesCalculationItemsModel.TaxesModel(String.valueOf(0.0)));

            taxesItems.add(model);
        }

        taxesModel.setItems(taxesItems);

        return taxesModel;
    }
}
