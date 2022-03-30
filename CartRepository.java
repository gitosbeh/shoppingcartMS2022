package com.shoppingcart.shoppingcartMs;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository 
public interface CartRepository  extends CrudRepository<Cart, Long>{
	
}
