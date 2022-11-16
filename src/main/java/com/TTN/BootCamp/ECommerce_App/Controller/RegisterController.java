package com.TTN.BootCamp.ECommerce_App.Controller;

import com.TTN.BootCamp.ECommerce_App.DTO.CustomerDTO;
import com.TTN.BootCamp.ECommerce_App.DTO.SellerDTO;
import com.TTN.BootCamp.ECommerce_App.DTO.UserDTO;
import com.TTN.BootCamp.ECommerce_App.Entity.Customer;
import com.TTN.BootCamp.ECommerce_App.Entity.EmailDetails;
import com.TTN.BootCamp.ECommerce_App.Entity.Seller;
import com.TTN.BootCamp.ECommerce_App.Entity.User;
import com.TTN.BootCamp.ECommerce_App.Repository.UserRepo;
import com.TTN.BootCamp.ECommerce_App.Service.MailService;
import com.TTN.BootCamp.ECommerce_App.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api")
public class RegisterController {

    private static class AccountResourceException extends RuntimeException {
        private AccountResourceException(String message) {
            super(message);
        }
    }

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private UserService userService;

    @Autowired
    MailService mailService;

    @PostMapping(path = "/register", headers = "Role=CUSTOMER")
    @ResponseStatus(HttpStatus.CREATED)
    public void registerCustomer(@Valid @RequestBody CustomerDTO customerDTO,@RequestHeader(value = "Role") String role) {

        Customer customer = userService.addCustomerDetails(customerDTO, role);
        EmailDetails emailDetails= new EmailDetails();
        emailDetails.setRecipient(customerDTO.getEmail());
        emailDetails.setSubject("Customer Registered");
        emailDetails.setMsgBody("A new customer has been registered");
        mailService.sendEmail(emailDetails);

    }

    @PostMapping(path = "/register", headers = "Role=SELLER")
    @ResponseStatus(HttpStatus.CREATED)
    public void registerSeller(@Valid @RequestBody SellerDTO sellerDTO,@RequestHeader(value = "Role") String role) {

        Seller seller = userService.addSellerDetails(sellerDTO, role);
        EmailDetails emailDetails= new EmailDetails();
        emailDetails.setRecipient(sellerDTO.getEmail());
        emailDetails.setSubject("Seller Registered");
        emailDetails.setMsgBody("A new seller has been registered");
        mailService.sendEmail(emailDetails);

    }
}
