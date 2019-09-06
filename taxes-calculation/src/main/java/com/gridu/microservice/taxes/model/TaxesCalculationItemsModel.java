package com.gridu.microservice.taxes.model;

import com.gridu.microservice.rest.validation.ValidationErrorType;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;


public class TaxesCalculationItemsModel {

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

		public String toString() {
			return "{ state="+state+"}";
		}
	}

	public static class TaxCalculationItemModel {

		private String category;
		@NotNull(message = ValidationErrorType.MISSING)
		private String id;
		private Double price;
		private TaxesModel taxes;
		
		public TaxCalculationItemModel() {
		}
		public TaxCalculationItemModel(String id, String category, Double price,
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

		public String getTax() {
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

		public String toString() {
			return "{ id="+id+
				", category="+category+
				", price="+price+
				"}";
		}
	}

	public static class TaxesModel {

		private String stateTax;

		public TaxesModel() {
		}

		public TaxesModel(String tax) {
			setStateTax(tax);
		}

		public String getStateTax() {
			return stateTax;
		}

		public void setStateTax(String stateTax) {
			this.stateTax = stateTax;
		}
	}

	private ShippingAddress address;

	@Valid
	private List<TaxCalculationItemModel> items = new ArrayList<TaxCalculationItemModel>();

	public List<TaxCalculationItemModel> getItems() {
		return items;
	}

	public String getStateCode() {
		return address.getState();
	}

	public void setAddress(ShippingAddress address) {
		this.address = address;
	}

	public void setItems(List<TaxCalculationItemModel> items) {
		this.items = items;
	}

	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("{");
		sb.append("address=").append(address);
		sb.append(", \nitems=").append(items);
		sb.append("}");
		return sb.toString();
	}
}
