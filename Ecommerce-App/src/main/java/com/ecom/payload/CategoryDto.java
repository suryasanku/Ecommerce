package com.ecom.payload;

import lombok.Data;

import javax.persistence.Column;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
public class CategoryDto {

    private Integer id;

    @NotBlank
    @Size(min = 4, max = 500)
    private String title;

    @NotBlank
    private String desc;


    private String bannerName;
}
