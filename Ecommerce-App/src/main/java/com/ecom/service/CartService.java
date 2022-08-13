package com.ecom.service;

import com.ecom.payload.CartDto;
import com.ecom.payload.CartItemRequest;

public interface CartService {

    CartDto addItemToCart(CartItemRequest cartItemRequest,String username);
    CartDto getCart(String username);
    CartDto removeCartItem(String username,Integer productId);
}
