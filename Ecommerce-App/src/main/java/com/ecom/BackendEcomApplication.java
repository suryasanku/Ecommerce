package com.ecom;

import com.ecom.entity.Role;
import com.ecom.entity.User;
import com.ecom.repository.RoleRepo;
import com.ecom.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@SpringBootApplication
public class BackendEcomApplication implements CommandLineRunner {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private RoleRepo roleRepo;

    public static void main(String[] args) {
        SpringApplication.run(BackendEcomApplication.class, args);
    }


    @Override
    public void run(String... args) throws Exception {

        try {
            Role role = new Role();
            role.setId(101);
            role.setName("ROLE_NORMAL");

            Role role1 = new Role();
            role1.setId(102);
            role1.setName("ROLE_ADMIN");
            List<Role> roles = List.of(role, role1);

            List<Role> roles1 = this.roleRepo.saveAll(roles);
            roles1.forEach(e -> {
                System.out.println(e.getName());
            });


        } catch (Exception e) {
            e.printStackTrace();
        }

//
//        Role role1 = new Role();
//        role1.setName("ROLE_ADMIN");
//
//        Role role2 = new Role();
//        role2.setName("ROLE_NORMAL");
//
//        Set<Role> roles = new HashSet<>();
//
//        roles.add(role1);
//        roles.add(role2);
//
//        User user = new User();
//        user.setName("Sharma Ji");
//        user.setEmail("sharma@gmail.com");
//        user.setPassword(this.passwordEncoder.encode("abc"));
//        user.setAddress("Lucknow");
//        user.setImageName("default.png");
//        user.setAbout("I am very good programmer.");
//        User save = this.userRepository.save(user);
//        System.out.println(save);
    }
}
