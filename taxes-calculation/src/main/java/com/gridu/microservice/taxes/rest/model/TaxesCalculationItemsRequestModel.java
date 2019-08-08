package com.gridu.microservice.taxes.rest.model;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import com.gridu.microservice.taxes.validation.ValidationErrorType;

public class TaxesCalculationItemsRequestModel {

	public static class ShippingAddress {
		private String state;

		public ShippingAddress() {
		}

		public ShippingAddress(String state) {
			setState(state);
		}

		public String getState() {
			return state;
		}

		public void setState(String state) {
			this.state = state;
		}
	}

	public static class TaxCalcualtionItemModel {

		private String category;
		@NotNull(message = ValidationErrorType.MISSING)
		private String id;
		private Double price;
		private TaxesModel taxes;
		
		public TaxCalcualtionItemModel() {
		}
		public TaxCalcualtionItemModel(String id, String category, Double price,
				TaxesModel taxes) {
			this.id = id;
			this.category = category;
			this.price = price;
			this.taxes = taxes;
		}

		public String getCategory() {
			return category;
		}

		public String getId() {
			return id;
		}

		public Double getPrice() {
			return price;
		}

		public TaxesModel getTaxes() {
			return taxes;
		}

		public Double getTax() {
			return taxes.getStateTax();
		}
		
		public void setCategory(String category) {
			this.category = category;
		}

		public void setId(String id) {
			this.id = id;
		}

		public void setPrice(Double price) {
			this.price = price;
		}

		public void setTaxes(TaxesModel taxes) {
			this.taxes = taxes;
		}
	}

	public static class TaxesModel {

		private Double stateTax;

		public TaxesModel() {
		}

		public TaxesModel(Double tax) {
			setStateTax(tax);
		}

		public Double getStateTax() {
			return stateTax;
		}

		public void setStateTax(Double stateTax) {
			this.stateTax = stateTax;
		}
	}

	private ShippingAddress address;

	@Valid
	private List<TaxCalcualtionItemModel> items = new ArrayList<TaxCalcualtionItemModel>();

	public List<TaxCalcualtionItemModel> getItems() {
		return items;
	}

	public String getStateCode() {
		return address.getState();
	}

	public void setAddress(ShippingAddress address) {
		this.address = address;
	}

	public void setItems(List<TaxCalcualtionItemModel> items) {
		this.items = items;
	}
}
