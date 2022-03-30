package com.shoppingcart.shoppingcartMs;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository 
public interface CartItemRepository  extends CrudRepository<CartItem, Long>{
	CartItem findByProduct(Product product);
}
