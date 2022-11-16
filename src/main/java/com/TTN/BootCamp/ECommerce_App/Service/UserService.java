package com.TTN.BootCamp.ECommerce_App.Service;

import com.TTN.BootCamp.ECommerce_App.DTO.CustomerDTO;
import com.TTN.BootCamp.ECommerce_App.DTO.SellerDTO;
import com.TTN.BootCamp.ECommerce_App.DTO.UserDTO;
import com.TTN.BootCamp.ECommerce_App.Entity.Customer;
import com.TTN.BootCamp.ECommerce_App.Entity.Seller;
import com.TTN.BootCamp.ECommerce_App.Entity.User;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface UserService {

    public User createUser(UserDTO userDTO);

    public Customer addCustomerDetails(CustomerDTO customerDTO, String role);

    public Seller addSellerDetails(SellerDTO sellerDTO, String role);

    @Transactional
    public List<User> getAllUsers();
}
