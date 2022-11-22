package com.TTN.BootCamp.ECommerce_App.Service;

import com.TTN.BootCamp.ECommerce_App.DTO.CustomerDTO;
import com.TTN.BootCamp.ECommerce_App.DTO.SellerDTO;
import com.TTN.BootCamp.ECommerce_App.DTO.UserDTO;
import com.TTN.BootCamp.ECommerce_App.Entity.Customer;
import com.TTN.BootCamp.ECommerce_App.Entity.Seller;
import com.TTN.BootCamp.ECommerce_App.Entity.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.mail.MessagingException;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Optional;

@Service
public interface UserService {

//    public User createUser(UserDTO userDTO);

    public String addCustomerDetails(CustomerDTO customerDTO, String role);

    public String addSellerDetails(SellerDTO sellerDTO, String role);

    @Transactional
    public List<User> getAllUsers();

    public String resetPassword(String token, String password, String confirmPassword);

    public String forgotPassword(String email);

    public String resendActivationMail(String email);

    public String activateUserAccount(String token);

//    public Optional<User> activateRegistration(String key);
}
