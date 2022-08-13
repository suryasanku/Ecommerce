package com.ecom.repository;

import com.ecom.entity.Category;
import com.ecom.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProductRepo extends JpaRepository<Product, Integer> {

    List<Product> findByCategory(Category category);

    List<Product> findByIsLiveFalse();

    List<Product> findByInStockFalse();

    @Query("SELECT p from  Product p where p.name like :xyz ")
    List<Product> searchProducts(@Param("xyz") String keywords);


}
