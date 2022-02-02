package com.devsup.dscatalog.tests;

import java.time.Instant;

import com.devsup.dscatalog.dto.ProductDTO;
import com.devsup.dscatalog.entities.Category;
import com.devsup.dscatalog.entities.Product;

public class Factory {
	
	public static Product createProduct() {
		Product product = new Product(1L, "Phone", "New phone", 800.0, "https://img.com/img.png", Instant.parse("2021-10-20T03:00:00Z"));
		product.getCategories().add(new Category(2L, "Electronics"));
		return product;
	}
	
	public static ProductDTO createProductDTO() {
		Product product = createProduct();
		return new ProductDTO(product, product.getCategories());
	}
	
	

}
