
package com.TTN.BootCamp.ECommerce_App.Controller;

import com.TTN.BootCamp.ECommerce_App.DTO.RequestDTO.AddressDTO;
import com.TTN.BootCamp.ECommerce_App.DTO.RequestDTO.PasswordDTO;
import com.TTN.BootCamp.ECommerce_App.DTO.ResponseDTO.CustomerResponseDTO;
import com.TTN.BootCamp.ECommerce_App.DTO.UpdateDTO.AddressUpdateDTO;
import com.TTN.BootCamp.ECommerce_App.DTO.UpdateDTO.CustomerUpdateDTO;
import com.TTN.BootCamp.ECommerce_App.Entity.Address;
import com.TTN.BootCamp.ECommerce_App.Exception.PasswordDoNotMatchException;
import com.TTN.BootCamp.ECommerce_App.Service.CustomerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;
import java.util.Locale;

@RestController
@RequestMapping("/api/customer")
public class CustomerController {
    Logger logger = LoggerFactory.getLogger(CustomerController.class);

    @Autowired
    CustomerService customerService;

    @GetMapping(path="/profile")
    @PreAuthorize("hasAuthority('CUSTOMER')")
    public ResponseEntity<CustomerResponseDTO> viewProfile(Authentication authentication) throws IOException {

        logger.info("SellerController: viewProfile started execution");
        Locale locale = LocaleContextHolder.getLocale();
        CustomerResponseDTO customer = customerService.showCustomerProfile(authentication.getName(), locale);
        logger.info("SellerController: viewProfile ended execution ");

        return new ResponseEntity<>(customer, HttpStatus.OK);
    }

    @PatchMapping(path = "/update_profile")
    @PreAuthorize("hasAuthority('CUSTOMER')")
    public ResponseEntity<String> updateProfile(Authentication authentication
            ,@RequestBody @Valid CustomerUpdateDTO customerDTO){
        logger.info("SellerController: updateProfile started execution");
        Locale locale = LocaleContextHolder.getLocale();
        String response = customerService.updateProfile(authentication.getName(), customerDTO, locale);
        logger.info("SellerController: updateProfile ended execution ");
        return new ResponseEntity<>(response,HttpStatus.OK);
    }

    @PatchMapping(path="/change_password")
    @PreAuthorize("hasAuthority('CUSTOMER')")
    public ResponseEntity<String> changePassword(Authentication authentication
            ,@Valid @RequestBody PasswordDTO passwordDTO){
        Locale locale = LocaleContextHolder.getLocale();
        if(!passwordDTO.getPassword().equals(passwordDTO.getConfirmPassword())) {
            throw new PasswordDoNotMatchException("Password do not match.");
        }
        String response = customerService
                .updatePassword(authentication.getName(), passwordDTO.getPassword(), locale);
        return new ResponseEntity<>(response,HttpStatus.OK);
    }

    @PatchMapping("/update_address")
    @PreAuthorize("hasAuthority('CUSTOMER')")
    public ResponseEntity<String> updateAddress(Authentication authentication
            ,@Valid @RequestBody AddressUpdateDTO addressDTO
            ,@RequestParam long id){
        Locale locale = LocaleContextHolder.getLocale();
        String email = authentication.getName();
        String responseMessage = customerService.updateAddress(email, addressDTO, id, locale);
        return new ResponseEntity<>(responseMessage,HttpStatus.OK);
    }

    @GetMapping(path="/list_address")
    @PreAuthorize("hasAuthority('CUSTOMER')")
    public ResponseEntity<List<Address>> viewAddresses(Authentication authentication){

        logger.info("SellerController: viewProfile started execution");
        Locale locale = LocaleContextHolder.getLocale();
        List<Address> addresses = customerService.showCustomerAddresses(authentication.getName(), locale);
        logger.info("SellerController: viewProfile ended execution ");
        return new ResponseEntity<>(addresses, HttpStatus.OK);
    }

    @PostMapping("/add_address")
    @PreAuthorize("hasAuthority('CUSTOMER')")
    public ResponseEntity<String> addNewAddress(Authentication authentication
            ,@Valid @RequestBody AddressDTO addressDTO){
        Locale locale = LocaleContextHolder.getLocale();
        String email = authentication.getName();
        String responseMessage = customerService.addAddress(email, addressDTO, locale);
        return new ResponseEntity<>(responseMessage,HttpStatus.OK);
    }

    @DeleteMapping("/delete_address")
    @PreAuthorize("hasAuthority('CUSTOMER')")
    public ResponseEntity<String> deleteAddress(Authentication authentication
            ,@RequestParam long id){
        Locale locale = LocaleContextHolder.getLocale();
        String email = authentication.getName();
        String responseMessage = customerService.deleteAddress(email, id, locale);
        return new ResponseEntity<>(responseMessage,HttpStatus.OK);
    }
}