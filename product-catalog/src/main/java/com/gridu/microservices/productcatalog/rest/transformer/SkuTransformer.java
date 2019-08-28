package com.gridu.microservices.productcatalog.rest.transformer;

import com.gridu.microservices.productcatalog.data.model.ClothingSku;
import com.gridu.microservices.productcatalog.data.model.ElectronicDeviceSku;
import com.gridu.microservices.productcatalog.data.model.Sku;
import com.gridu.microservices.productcatalog.rest.model.SkuRequest;
import org.springframework.stereotype.Component;

@Component
public class SkuTransformer {

	public Sku fromSkuRequest(SkuRequest skuRequest) {
		Sku sku = null;
		if (skuRequest.getColor() != null) {
			ElectronicDeviceSku electronicDeviceSku = new ElectronicDeviceSku();
			electronicDeviceSku.setColor(skuRequest.getColor());
			sku = electronicDeviceSku;
		} else if (skuRequest.getSize() != null) {
			ClothingSku clothingSku = new ClothingSku();
			clothingSku.setSize(skuRequest.getSize());
			sku = clothingSku;
		}
		if (sku == null) {
			sku = new Sku();
		}
		sku.setId(skuRequest.getId());
		return sku;
	}
}
