package com.gridu.microservices.product.catalog.service;

import java.util.Collection;
import java.util.function.Predicate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;

import com.gridu.microservices.product.catalog.common.ProductCategoryEnum;
import com.gridu.microservices.product.catalog.dao.ProductRepository;
import com.gridu.microservices.product.catalog.model.Product;
import com.gridu.microservices.product.catalog.model.ProductSku;

@Service
public class ProductService extends GenericRepositoryService<Product, Long> {

	@Autowired
	private ProductRepository productRepository;

	@Autowired
	private SkuTypeService skuTypeService;

	public Product findUniqueProduct(String name, String category) {
		return productRepository.findUniqueProduct(name, category);
	}

	public ProductSku getDefaultSkuType(Product product) {

		ProductSku productSku = null;
		ProductCategoryEnum category = ProductCategoryEnum.getProductCategory(product.getCategory().getName());

		switch (category) {
		case CLOTHES:
		case ELECTRONIC_DEVICES:
			productSku = new ProductSku();
			productSku.setProduct(product);
			productSku.setSkuType(getSkuTypeService().findByValue(category.getDefaultSkuType()));
			break;
		default:
			break;
		}
		return productSku;
	}
	
	public ProductSku findSkuById(Long productId, Long skuId) {
		Predicate<ProductSku> predicate = p->p.getId() == skuId;	
		Collection<ProductSku> childSkus = this.findById(productId).get().getChildSkus();
		return childSkus.stream().filter(predicate).findFirst().orElseGet(() -> null);
	}

	
	public boolean isExistingProduct(Long productId) {
		return getRepository().findById(productId).isPresent();
	}
	
	private SkuTypeService getSkuTypeService() {
		return skuTypeService;
	}

	@Override
	protected CrudRepository<Product, Long> getRepository() {
		return productRepository;
	}
}
