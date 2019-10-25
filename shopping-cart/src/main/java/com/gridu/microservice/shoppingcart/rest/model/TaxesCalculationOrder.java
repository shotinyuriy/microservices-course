package com.gridu.microservice.shoppingcart.rest.model;

import java.util.ArrayList;
import java.util.List;

public class TaxesCalculationOrder {
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
		private Taxes taxes;

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

		public Taxes getTaxes() {
			return taxes;
		}

		public void setTaxes(Taxes taxes) {
			this.taxes = taxes;
		}
	}

	public static class Taxes {
		private double stateTax;

		public double getStateTax() {
			return stateTax;
		}

		public void setStateTax(double stateTax) {
			this.stateTax = stateTax;
		}
	}
}
