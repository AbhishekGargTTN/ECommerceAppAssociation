package com.TTN.BootCamp.ECommerce_App.Service;

import com.TTN.BootCamp.ECommerce_App.DTO.RequestDTO.CustomerDTO;
import com.TTN.BootCamp.ECommerce_App.DTO.RequestDTO.SellerDTO;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.util.Locale;

@Service
public interface UserService {

//    public User createUser(UserDTO userDTO);

    public String addCustomerDetails(CustomerDTO customerDTO, String role, Locale locale);

    public String addSellerDetails(SellerDTO sellerDTO, String role, Locale locale);

    public String resetPassword(String token, String password, String confirmPassword, Locale locale);

    public String forgotPassword(String email, Locale locale);

    public String resendActivationMail(String email, Locale locale);

    public String activateUserAccount(String token, Locale locale);

    public String uploadImage(@RequestParam("file") MultipartFile file, String email, Locale locale);

}
