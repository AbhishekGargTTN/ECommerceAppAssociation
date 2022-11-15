package com.TTN.BootCamp.ECommerce_App.Controller;

import com.TTN.BootCamp.ECommerce_App.DTO.UserDTO;
import com.TTN.BootCamp.ECommerce_App.Entity.User;
import com.TTN.BootCamp.ECommerce_App.Repository.UserRepo;
import com.TTN.BootCamp.ECommerce_App.Service.ServiceImpl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

@RestController
@RequestMapping("/api")
public class UserController {

    @Autowired
    private  UserRepo userRepo;
    @Autowired
    private UserServiceImpl userService;

    @PostMapping("/users")
//    @PreAuthorize("hasAuthority(\"" + AuthoritiesConstants.ADMIN + "\")")
    public ResponseEntity<User> createUser(@Valid @RequestBody UserDTO userDTO) throws URISyntaxException {

            User newUser = userService.createUser(userDTO);
            return ResponseEntity.created(new URI("/api/users/" + newUser.getEmail()))
//                    .headers(HeaderUtil.createAlert(applicationName,  "A user is created with identifier " + newUser.getEmail(), newUser.getEmail()))
                    .body(newUser);
//        }
    }

    @GetMapping("/users")
    public List<User> getAllUsers() {
         List<User> users = userService.getAllUsers();
        return users;
    }
}
