package com.ecom.payload;

import lombok.Data;

@Data
public class CartItemRequest {

    private Integer productId;
    private Integer quantity;

}
