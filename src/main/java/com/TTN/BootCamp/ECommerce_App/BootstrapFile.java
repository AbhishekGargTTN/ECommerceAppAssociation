package com.TTN.BootCamp.ECommerce_App;

import com.TTN.BootCamp.ECommerce_App.Entity.Role;
import com.TTN.BootCamp.ECommerce_App.Entity.User;
import com.TTN.BootCamp.ECommerce_App.Repository.RoleRepo;
import com.TTN.BootCamp.ECommerce_App.Repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class BootstrapFile implements CommandLineRunner {
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private RoleRepo roleRepo;

    @Override
    public void run(String... args) throws Exception {

        if(roleRepo.count()<1){

            Role role1 = new Role();
//            role1.setId(1);
            role1.setRole("ADMIN");

            Role role2 = new Role();
//            role2.setId(2);
            role2.setRole("SELLER");

            Role role3 = new Role();
//            role3.setId(3);
            role3.setRole("CUSTOMER");

            roleRepo.save(role1);
            roleRepo.save(role2);
            roleRepo.save(role3);
        }
        if(userRepo.count()<1) {

            PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
            User user = new User();
            user.setFirstName("Super");
            user.setLastName("User");
            user.setEmail("superuser@gmail.com");
            user.setPassword(passwordEncoder.encode("supersecret"));
            Role role = roleRepo.findById(1l).get();
            user.setRole(role);
            userRepo.save(user);
        }
    }
}
