package com.shoppingcart.shoppingcartMs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CartController {

	@Autowired
	CartService cartService;
		
	@GetMapping("/product/list")
	public ApiResponse listProducts () {
		 return cartService.getProduct();
	}
	
	@GetMapping("/catalog/list")
	public ApiResponse listCatalog () {
		 return cartService.getCatalog();
	}
	@GetMapping("/catalog/size")
	public ApiResponse catalogSize () {
		 return cartService.catalogSize();
	}
	
	@PostMapping("/cart/add")
	public ApiResponse addToCart(@RequestParam String id) {
		return cartService.addToCart(id);
	}
	@DeleteMapping("/cart/remove")
	public ApiResponse deleteItemFromCart(@RequestParam  String id) {
		return cartService.deleteItemFromCart(id);
	}
	
	@PostMapping("/cart/checkout")
	public ApiResponse checkout() {
		return cartService.checkout();
	}
	
}