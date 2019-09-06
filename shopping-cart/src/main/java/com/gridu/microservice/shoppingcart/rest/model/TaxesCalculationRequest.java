package com.gridu.microservice.shoppingcart.rest.model;

import com.gridu.microservice.shoppingcart.data.model.Address;

import java.util.ArrayList;
import java.util.List;

public class TaxesCalculationRequest {

	private TaxCalculationAddress address = new TaxCalculationAddress();
	private List<TaxCalculationItem> items = new ArrayList<>();

	public TaxCalculationAddress getAddress() {
		return address;
	}

	public void setAddress(TaxCalculationAddress address) {
		this.address = address;
	}

	public List<TaxCalculationItem> getItems() {
		return items;
	}

	public void setItems(List<TaxCalculationItem> items) {
		this.items = items;
	}

	public static class TaxCalculationAddress {
		private String state;

		public String getState() {
			return state;
		}

		public void setState(String state) {
			this.state = state;
		}
	}

	public static class TaxCalculationItem {
		private String id;
		private String category;
		private double price;

		public String getId() {
			return id;
		}

		public void setId(String id) {
			this.id = id;
		}

		public String getCategory() {
			return category;
		}

		public void setCategory(String category) {
			this.category = category;
		}

		public double getPrice() {
			return price;
		}

		public void setPrice(double price) {
			this.price = price;
		}
	}
}
