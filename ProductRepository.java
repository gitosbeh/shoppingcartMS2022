package com.shoppingcart.shoppingcartMs;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository 
public interface ProductRepository  extends CrudRepository<Product, Long>{
	
	List<Product> findByQuantityGreaterThan(int quantity);
	Product findByProdId(String prodId);
}
