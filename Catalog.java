package com.shoppingcart.shoppingcartMs;

import java.util.ArrayList;
import java.util.List;

public class Catalog {
	public List<Product> getCatalogProduct() {
		return catalogProduct;
	}

	public void setCatalogProduct(List<Product> catalogProduct) {
		this.catalogProduct = catalogProduct;
	}

	List<Product> catalogProduct = new ArrayList<Product>(); 
}
