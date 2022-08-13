package com.ecom.service.impl;

import com.ecom.entity.Cart;
import com.ecom.entity.CartItem;
import com.ecom.entity.Product;
import com.ecom.entity.User;
import com.ecom.exception.ResourceNotFoundException;
import com.ecom.payload.CartDto;
import com.ecom.payload.CartItemRequest;
import com.ecom.repository.CartRepo;
import com.ecom.repository.ProductRepo;
import com.ecom.repository.UserRepository;
import com.ecom.service.CartService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

@Service
public class CartServiceImpl implements CartService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProductRepo productRepo;

    @Autowired
    private CartRepo cartRepo;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public CartDto addItemToCart(CartItemRequest cartItemRequest, String username) {

        User user = this.userRepository.findByEmail(username).orElseThrow(() -> new ResourceNotFoundException("user", "username", username));
        Product product = this.productRepo.findById(cartItemRequest.getProductId()).orElseThrow(() -> new ResourceNotFoundException("product ", "product id ", cartItemRequest.getProductId() + ""));
        CartItem cartItem = new CartItem();
        cartItem.setProduct(product);
        cartItem.setQuantity(cartItemRequest.getQuantity());
        Cart cart = user.getCart();
        if (cart == null) {
            cart = new Cart();
        }
        cartItem.setCart(cart);
        cart.setUser(user);
        Set<CartItem> items = cart.getItems();
        AtomicReference<Boolean> updatedQuantity = new AtomicReference<>(false);
        Set<CartItem> newCartItems = items.stream().map((cartItem1 -> {
            if (cartItem1.getProduct().getId() == product.getId()) {
                cartItem1.setQuantity(cartItemRequest.getQuantity());
                updatedQuantity.set(true);
            }
            return cartItem1;
        })).collect(Collectors.toSet());
        if (updatedQuantity.get()) {
            cart.setItems(newCartItems);
        } else {
            items.add(cartItem);
        }
        Cart updatedCart = this.cartRepo.save(cart);
        System.err.println(updatedCart.getUser());
        return this.modelMapper.map(updatedCart, CartDto.class);
    }

    @Override
    public CartDto getCart(String username) {
        User user = this.userRepository.findByEmail(username).orElseThrow(() -> new ResourceNotFoundException("User ", " username ", username));
        Cart cart = user.getCart();
        return this.modelMapper.map(cart, CartDto.class);
    }

    @Override
    public CartDto removeCartItem(String username, Integer productId) {
        User user = this.userRepository.findByEmail(username).orElseThrow(() -> new ResourceNotFoundException("User ", " username ", username));
        Cart cart = this.cartRepo.findByUser(user);
        Set<CartItem> items = cart.getItems();
        boolean b = items.removeIf((item) -> item.getProduct().getId().equals(productId));
        System.out.println(b);
        Cart car1 = this.cartRepo.save(cart);
        System.out.println(car1.getItems().size());
        return this.modelMapper.map(car1, CartDto.class);
    }
}
