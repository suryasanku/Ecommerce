package com.ecom.payload;

import com.ecom.entity.Product;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class CartItemDto {

    private Integer id;
    private ProductDto product;
    private Integer quantity;

    @JsonProperty("total_price")
    public Double getTotalPrice()
    {
        return quantity*this.product.getPrice();
    }


}
