package com.gridu.microservices.product.catalog.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;

import com.gridu.microservices.product.catalog.dao.SkuTypeRepository;
import com.gridu.microservices.product.catalog.model.SkuType;

@Service
public class SkuTypeService extends GenericRepositoryService<SkuType, Integer> {

	@Autowired
	private SkuTypeRepository skuTypeRepository;
	
	public SkuType findByValue(String value) {
		return skuTypeRepository.findByValue(value);
	}

	@Override
	protected CrudRepository<SkuType, Integer> getRepository() {
		return skuTypeRepository;
	}
}
