package com.TTN.BootCamp.ECommerce_App.Controller;

import com.TTN.BootCamp.ECommerce_App.DTO.CustomerDTO;
import com.TTN.BootCamp.ECommerce_App.DTO.SellerDTO;
import com.TTN.BootCamp.ECommerce_App.Entity.*;
import com.TTN.BootCamp.ECommerce_App.Repository.SecureTokenRepo;
import com.TTN.BootCamp.ECommerce_App.Repository.UserRepo;
import com.TTN.BootCamp.ECommerce_App.Service.MailService;
import com.TTN.BootCamp.ECommerce_App.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.UnsupportedEncodingException;
import java.util.Optional;

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

    @Autowired
    SecureTokenRepo secureTokenRepo;

    @PostMapping(path = "/register", headers = "Role=CUSTOMER")
    @ResponseStatus(HttpStatus.CREATED)
    public void registerCustomer(@Valid @RequestBody CustomerDTO customerDTO,@RequestHeader(value = "Role") String role, HttpServletRequest request) throws MessagingException, UnsupportedEncodingException {

        Customer customer = userService.addCustomerDetails(customerDTO, role, getSiteURL(request));
        SecureToken secureToken =new SecureToken();
//        EmailDetails emailDetails= new EmailDetails();
//        emailDetails.setRecipient(customerDTO.getEmail());
//        emailDetails.setSubject("Customer Registered");
//        emailDetails.setMsgBody("A new customer has been registered");
//        mailService.sendEmail(emailDetails);
    }

    private String getSiteURL(HttpServletRequest request) {
        String siteURL = request.getRequestURL().toString();
        return siteURL.replace(request.getServletPath(), "");
    }
    @PostMapping(path = "/register", headers = "Role=SELLER")
    @ResponseStatus(HttpStatus.CREATED)
    public void registerSeller(@Valid @RequestBody SellerDTO sellerDTO,@RequestHeader(value = "Role") String role) {

        Seller seller = userService.addSellerDetails(sellerDTO, role);
//        EmailDetails emailDetails= new EmailDetails();
//        emailDetails.setRecipient(sellerDTO.getEmail());
//        emailDetails.setSubject("Seller Registered");
//        emailDetails.setMsgBody("A new seller has been registered");
//        mailService.sendEmail(emailDetails);
    }

//    @GetMapping("/activate")
//    public String  activateAccount(@Param("key") String key) {
//        Optional<User> user = userService.activateRegistration(key);
//        return "Account Activated Successfully";
//    }

    @RequestMapping(value="/activate-account", method= {RequestMethod.GET, RequestMethod.POST})
    public ResponseEntity<String > confirmUserAccount(@RequestParam("token")String confirmationToken)
    {
        SecureToken token = secureTokenRepo.findBySecureToken(confirmationToken);
        System.out.println(token);
        if(token != null)
        {
            User user = userRepo.findOneByEmail(token.getUser().getEmail());
            user.setActive(true);
            userRepo.save(user);
            System.out.println("verified");
            return new ResponseEntity<>("Activation successful",HttpStatus.OK) ;
        }
        else
        {
            return new ResponseEntity<>("unsuccessful",HttpStatus.OK) ;
        }


    }

}
