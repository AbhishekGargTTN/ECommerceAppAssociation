package com.TTN.BootCamp.ECommerce_App.Controller;

import com.TTN.BootCamp.ECommerce_App.DTO.CustomerDTO;
import com.TTN.BootCamp.ECommerce_App.DTO.SellerDTO;
import com.TTN.BootCamp.ECommerce_App.Repository.SecureTokenRepo;
import com.TTN.BootCamp.ECommerce_App.Repository.UserRepo;
import com.TTN.BootCamp.ECommerce_App.Service.MailService;
import com.TTN.BootCamp.ECommerce_App.Service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api")
public class RegisterController {

    Logger logger = LoggerFactory.getLogger(RegisterController.class);

//    private static class AccountResourceException extends RuntimeException {
//        private AccountResourceException(String message) {
//            super(message);
//        }
//    }

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private UserService userService;

    @Autowired
    MailService mailService;

    @Autowired
    SecureTokenRepo secureTokenRepo;

    @PostMapping(path = "/register", headers = "Role=CUSTOMER")
    @ResponseStatus(HttpStatus.CREATED)
    public String registerCustomer(@Valid @RequestBody CustomerDTO customerDTO,@RequestHeader(value = "Role") String role){

        logger.info("RegisterController: registering Customer");
        return userService.addCustomerDetails(customerDTO, role);
    }

    @PostMapping(path = "/register", headers = "Role=SELLER")
    @ResponseStatus(HttpStatus.CREATED)
    public String  registerSeller(@Valid @RequestBody SellerDTO sellerDTO,@RequestHeader(value = "Role") String role) {

        logger.info("RegisterController: registering Seller");

        return userService.addSellerDetails(sellerDTO, role);
    }



}
