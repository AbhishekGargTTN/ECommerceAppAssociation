package com.TTN.BootCamp.ECommerce_App.Controller;

import com.TTN.BootCamp.ECommerce_App.DTO.AddressDTO;
import com.TTN.BootCamp.ECommerce_App.DTO.CustomerDTO;
import com.TTN.BootCamp.ECommerce_App.DTO.PasswordDTO;
import com.TTN.BootCamp.ECommerce_App.DTO.SellerDTO;
import com.TTN.BootCamp.ECommerce_App.DTO.UpdateDTO.AddressUpdateDTO;
import com.TTN.BootCamp.ECommerce_App.DTO.UpdateDTO.CustomerUpdateDTO;
import com.TTN.BootCamp.ECommerce_App.DTO.UpdateDTO.SellerUpdateDTO;
import com.TTN.BootCamp.ECommerce_App.Entity.Address;
import com.TTN.BootCamp.ECommerce_App.Exception.PasswordDoNotMatchException;
import com.TTN.BootCamp.ECommerce_App.Service.CustomerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/customer")
public class CustomerController {
    Logger logger = LoggerFactory.getLogger(CustomerController.class);

    @Autowired
    CustomerService customerService;

    @GetMapping(path="/profile")
    @PreAuthorize("hasAuthority('CUSTOMER')")
    public ResponseEntity<CustomerDTO> viewProfile(Authentication authentication){

        logger.info("SellerController: viewProfile started execution");
        CustomerDTO customer = customerService.showCustomerProfile(authentication.getName());
        logger.info("SellerController: viewProfile ended execution ");
        return new ResponseEntity<>(customer, HttpStatus.OK);
    }

    @PatchMapping(path = "/update_profile")
    @PreAuthorize("hasAuthority('CUSTOMER')")
    public ResponseEntity<String> updateProfile(Authentication authentication
            ,@RequestBody @Valid CustomerUpdateDTO customerDTO){
        logger.info("SellerController: updateProfile started execution");
        String response = customerService.updateProfile(authentication.getName(), customerDTO);
        logger.info("SellerController: updateProfile ended execution ");
        return new ResponseEntity<>(response,HttpStatus.OK);
    }

    @PatchMapping(path="/change_password")
    @PreAuthorize("hasAuthority('CUSTOMER')")
    public ResponseEntity<String> changePassword(Authentication authentication
            ,@Valid @RequestBody PasswordDTO passwordDTO){
        if(!passwordDTO.getPassword().equals(passwordDTO.getConfirmPassword())) {
            throw new PasswordDoNotMatchException("Password do not match.");
        }
            String response = customerService.updatePassword(authentication.getName(), passwordDTO.getPassword());
        return new ResponseEntity<>(response,HttpStatus.OK);
    }

    @PatchMapping("/update_address")
    @PreAuthorize("hasAuthority('CUSTOMER')")
    public ResponseEntity<String> updateAddress(Authentication authentication
            ,@Valid @RequestBody AddressUpdateDTO addressDTO
            ,@RequestParam long id){
        String email = authentication.getName();
        String responseMessage = customerService.updateAddress(email,addressDTO,id);
        return new ResponseEntity<>(responseMessage,HttpStatus.OK);
    }

    @GetMapping(path="/list_address")
    @PreAuthorize("hasAuthority('CUSTOMER')")
    public ResponseEntity<List<Address>> viewAddresses(Authentication authentication){

        logger.info("SellerController: viewProfile started execution");
        List<Address> addresses = customerService.showCustomerAddresses(authentication.getName());
        logger.info("SellerController: viewProfile ended execution ");
        return new ResponseEntity<>(addresses, HttpStatus.OK);
    }

    @PostMapping("/add_address")
    @PreAuthorize("hasAuthority('CUSTOMER')")
    public ResponseEntity<String> addNewAddress(Authentication authentication
            ,@Valid @RequestBody AddressDTO addressDTO){
        String email = authentication.getName();
        String responseMessage = customerService.addAddress(email,addressDTO);
        return new ResponseEntity<>(responseMessage,HttpStatus.OK);
    }

    @DeleteMapping("/delete_address")
    @PreAuthorize("hasAuthority('CUSTOMER')")
    public ResponseEntity<String> deleteAddress(Authentication authentication
            ,@RequestParam long id){
        String email = authentication.getName();
        String responseMessage = customerService.deleteAddress(email,id);
        return new ResponseEntity<>(responseMessage,HttpStatus.OK);
    }
}
