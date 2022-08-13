package com.ecom.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;

    private String description;

    private Double price;

    private Boolean isLive = false;

    private Boolean inStock = true;

    private Integer rating;

    private String imageName;

    @ManyToOne(fetch = FetchType.LAZY)
    private Category category;


}
