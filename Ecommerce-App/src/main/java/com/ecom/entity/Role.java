package com.ecom.entity;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Service;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Objects;

@Entity

@Getter
@Setter
public class Role {

    @Id
    private Integer id;

    private String name;

    @Override
    public boolean equals(Object o) {
        Role role1 = (Role) o;
        return this.getId().equals(role1.getId());
    }


}
