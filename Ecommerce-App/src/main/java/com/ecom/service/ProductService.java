package com.ecom.service;

import com.ecom.payload.ProductDto;

import java.io.IOException;
import java.util.List;

public interface ProductService {


    //create

    ProductDto create(ProductDto product, Integer catId);

    //update

    ProductDto update(ProductDto product, Integer productId);

    ProductDto getById(Integer productId);

    //delete

    void delete(Integer productId);

    List<ProductDto> getAllProducts();

    List<ProductDto> getAllProductOfCategory(Integer catId);

    //all products that is not live

    List<ProductDto> getProductNotLive();

    //all product that is outof stock

    List<ProductDto> getProductOutOfStock();

    // business

    List<ProductDto> searchProduct(String titleKey);
    
    //delete product
    
    void deleteProductImage(Integer ProductId,String prodctPath) throws IOException;


}
