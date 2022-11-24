package com.TTN.BootCamp.ECommerce_App.Service;

import com.TTN.BootCamp.ECommerce_App.DTO.RequestDTO.CustomerDTO;
import com.TTN.BootCamp.ECommerce_App.DTO.RequestDTO.SellerDTO;
import com.TTN.BootCamp.ECommerce_App.Entity.User;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

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

    public String uploadImage(@RequestParam("file") MultipartFile file, String email);

//    public Optional<User> activateRegistration(String key);
}
