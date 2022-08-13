package com.ecom.payload;

import lombok.Data;

@Data
public class OrderItemDto {
    private Integer id;
    private ProductDto product;
    private Integer quantity;
}
