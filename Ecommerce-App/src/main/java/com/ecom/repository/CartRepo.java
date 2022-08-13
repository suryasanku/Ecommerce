package com.ecom.repository;

import com.ecom.entity.Cart;
import com.ecom.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartRepo extends JpaRepository<Cart,Integer> {

    Cart findByUser(User user);
}
