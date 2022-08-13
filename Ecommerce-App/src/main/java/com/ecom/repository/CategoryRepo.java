package com.ecom.repository;

import com.ecom.entity.Category;
import com.ecom.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CategoryRepo extends JpaRepository<Category, Integer> {


}
