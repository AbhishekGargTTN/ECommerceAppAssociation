package com.TTN.BootCamp.ECommerce_App.Service;

import com.TTN.BootCamp.ECommerce_App.DTO.UserDTO;
import com.TTN.BootCamp.ECommerce_App.Entity.User;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface UserService {

    public User createUser(UserDTO userDTO);

    public User registerUser(UserDTO userDTO, String password);

    @Transactional
    public List<User> getAllUsers();
}
