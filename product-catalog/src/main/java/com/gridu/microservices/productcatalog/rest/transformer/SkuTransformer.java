package com.gridu.microservices.productcatalog.rest.transformer;

import com.gridu.microservices.productcatalog.data.model.ClothingSku;
import com.gridu.microservices.productcatalog.data.model.ElectronicDeviceSku;
import com.gridu.microservices.productcatalog.data.model.ProductCategory;
import com.gridu.microservices.productcatalog.data.model.Sku;
import com.gridu.microservices.productcatalog.rest.model.SkuRequest;
import org.springframework.stereotype.Component;

@Component
public class SkuTransformer {

	public Sku fromSkuRequest(SkuRequest skuRequest, Sku sku, String productCategory) {
		Sku newSku = null;
		if (ProductCategory.ELECTRONIC_DEVICES.equalsIgnoreCase(productCategory)) {
			ElectronicDeviceSku electronicDeviceSku;
			if (sku instanceof ElectronicDeviceSku) {
				electronicDeviceSku = (ElectronicDeviceSku) sku;
			} else {
				electronicDeviceSku = new ElectronicDeviceSku();
			}
			if(skuRequest.getColor() != null) {
				electronicDeviceSku.setColor(skuRequest.getColor());
			}
			newSku = electronicDeviceSku;
		} else if (ProductCategory.CLOTHING.equalsIgnoreCase(productCategory)) {
			ClothingSku clothingSku;
			if (sku instanceof ClothingSku) {
				clothingSku = (ClothingSku) sku;
			} else {
				clothingSku = new ClothingSku();
			}
			if (skuRequest.getSize() != null) {
				clothingSku.setSize(skuRequest.getSize());
			}
			newSku = clothingSku;
		}
		if (newSku == null) {
			newSku = new Sku();
		}
		newSku.setId(skuRequest.getId());
		return newSku;
	}
}
