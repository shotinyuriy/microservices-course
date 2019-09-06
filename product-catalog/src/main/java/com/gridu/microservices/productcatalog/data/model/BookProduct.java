package com.gridu.microservices.productcatalog.data.model;

import javax.persistence.Entity;
import java.util.Set;

@Entity
public class BookProduct extends Product {

	private String author;

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}
}
