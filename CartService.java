package com.shoppingcart.shoppingcartMs;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class CartService {

	List<Product> products = new ArrayList<>();

	@Autowired
    private CartRepository cartRepository;
	
	@Autowired
	private CartItemRepository cartItemRepository;
	
	
	@Autowired
	private ProductRepository productRepository;

    
	public ApiResponse getProduct() {
		List<Product> productList = readProductsFromJson();	
		for (Product product : productList) {
			productRepository.save(product);
		}
		
		Iterable<Product> products =  productRepository.findAll();
		return new ApiResponse(HttpStatus.OK.value(),"SUCCESS", products);
	}
	
	public ApiResponse getCatalog() {
		List<Product> productList =  (List<Product>) productRepository.findByQuantityGreaterThan(0);
		return new ApiResponse(HttpStatus.OK.value(),"SUCCESS", productList);
	}

	private List<Product> readProductsFromJson() {
		ObjectMapper mapper = new ObjectMapper();
		List<Product> productList = null;
		TypeReference<List<Product>> mapType = new TypeReference<List<Product>>() {};
		InputStream is = TypeReference.class.getResourceAsStream("/data/product.json");
		try {
			productList = mapper.readValue(is, mapType);
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
		return productList;
	}

	public ApiResponse catalogSize() {
		List<Product> productList =  (List<Product>) productRepository.findByQuantityGreaterThan(0);
		return new ApiResponse(HttpStatus.OK.value(),"SUCCESS", productList.size());
	}

	public ApiResponse addToCart(String prodId) {
		Product product = productRepository.findByProdId(prodId);
		if (product!=null && product.getQuantity() > 0) {
			//add to cart
			Cart cart = new Cart();
			CartItem item = new CartItem();
			item.setProduct(product);
			item.setQuantity(1);
			Set<CartItem> items = new HashSet<CartItem>();
			items.add(item);
			cart.setItems(items);
			cartRepository.save(cart);
			item.setCart(cart);
			item.setProduct(product);
			cartItemRepository.save(item);
			//update the quantity to zero on the product table
			product.setQuantity(0);
			productRepository.save(product);
			return new ApiResponse(HttpStatus.OK.value(),"SUCCESS", cartRepository.findAll());
		} else {
			return new ApiResponse(HttpStatus.NOT_FOUND.value(), "FAIL", "Products are not Avaialbel in the Catalog");
		}
	}
	
	public ApiResponse deleteItemFromCart(String prodId) {
		Product product = productRepository.findByProdId(prodId);
		if(product!=null) {
			CartItem item = cartItemRepository.findByProduct(product);
			cartItemRepository.delete(item);
			return new ApiResponse(HttpStatus.OK.value(),"SUCCESS", cartRepository.findAll());
		} else {
			return new ApiResponse(HttpStatus.NOT_FOUND.value(), "FAIL", "Product already removed from the Cart");
		}
	}
	
	public ApiResponse checkout() {
		cartRepository.deleteAll();
		return new ApiResponse(HttpStatus.OK.value(),"SUCCESS", cartRepository.findAll());
	}
	
}
