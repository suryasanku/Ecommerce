package com.ecom.payload;

import com.ecom.entity.CartItem;
import com.ecom.entity.User;
import lombok.Data;

import java.util.HashSet;
import java.util.Set;

@Data
public class CartDto {
    private Integer id;
    private UserDto user;
    private Set<CartItemDto> items = new HashSet<>();

}
