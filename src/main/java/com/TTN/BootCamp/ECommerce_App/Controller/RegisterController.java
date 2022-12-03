package com.TTN.BootCamp.ECommerce_App.Controller;

import com.TTN.BootCamp.ECommerce_App.DTO.RequestDTO.CustomerDTO;
import com.TTN.BootCamp.ECommerce_App.DTO.RequestDTO.SellerDTO;
import com.TTN.BootCamp.ECommerce_App.Repository.SecureTokenRepo;
import com.TTN.BootCamp.ECommerce_App.Repository.UserRepo;
import com.TTN.BootCamp.ECommerce_App.Service.MailService;
import com.TTN.BootCamp.ECommerce_App.Service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Locale;

@RestController
@RequestMapping("/api")
public class RegisterController {

    Logger logger = LoggerFactory.getLogger(RegisterController.class);

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
        Locale locale = LocaleContextHolder.getLocale();
        return userService.addCustomerDetails(customerDTO, role, locale);
    }

    @PostMapping(path = "/register", headers = "Role=SELLER")
    @ResponseStatus(HttpStatus.CREATED)
    public String  registerSeller(@Valid @RequestBody SellerDTO sellerDTO,@RequestHeader(value = "Role") String role) {

        logger.info("RegisterController: registering Seller");
        Locale locale = LocaleContextHolder.getLocale();
        return userService.addSellerDetails(sellerDTO, role, locale);
    }



}
