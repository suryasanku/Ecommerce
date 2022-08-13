package com.ecom.repository;

import com.ecom.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;


public interface RoleRepo extends JpaRepository<Role, Integer> {

    Role findByName(String name);

}
