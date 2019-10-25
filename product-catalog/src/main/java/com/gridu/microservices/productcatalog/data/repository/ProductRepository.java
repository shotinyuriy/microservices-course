package com.gridu.microservices.productcatalog.data.repository;

import com.gridu.microservices.productcatalog.data.model.Product;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends CrudRepository<Product, String> {

//	@Query("SELECT u FROM Product p, Sku s WHERE s.product = p AND s.id = skuId")
	Product findProductByChildSkus_Id(String skuId);
}
