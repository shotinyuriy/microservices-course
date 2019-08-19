package com.gridu.microservices.productcatalog;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gridu.microservices.productcatalog.dao.DataRepository;
import com.gridu.microservices.productcatalog.model.Product;

@RestController
public class TestController {

    @Autowired
    private DataSource dataSource;
    
    @Autowired
    private DataRepository productDataRepository;
    
	@GetMapping(value = "/ok")
	public String ok() {

		System.out.println("Data source: "+dataSource.toString());
		Product product = new Product();
		product.setName("books");
		
		productDataRepository.save(product);
		
		Iterable<Product> products = productDataRepository.findAll();
		for (Product p : products) {
			System.out.println(p.getId()+"____"+p.getName());
		}
		return "OK response";
	}
}
