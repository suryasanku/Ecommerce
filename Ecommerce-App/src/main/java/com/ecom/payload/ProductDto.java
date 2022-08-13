package com.ecom.payload;

import com.ecom.entity.Category;
import lombok.Data;

@Data
public class ProductDto {


    private Integer id;

    private String name;

    private String description;

    private Double price;

    private Boolean isLive = false;

    private Boolean inStock = true;

    private Integer rating;

    private String imageName;

    private CategoryDto category;

    private String brandName = "learn Code With Durgesh";

}
