package com.TTN.BootCamp.ECommerce_App.Controller;

import com.TTN.BootCamp.ECommerce_App.DTO.UpdateDTO.AddressUpdateDTO;
import com.TTN.BootCamp.ECommerce_App.DTO.RequestDTO.PasswordDTO;
import com.TTN.BootCamp.ECommerce_App.DTO.RequestDTO.SellerDTO;
import com.TTN.BootCamp.ECommerce_App.DTO.UpdateDTO.SellerUpdateDTO;
import com.TTN.BootCamp.ECommerce_App.Exception.PasswordDoNotMatchException;
import com.TTN.BootCamp.ECommerce_App.Service.SellerService;
import org.springframework.security.core.Authentication;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.IOException;

@RestController
@RequestMapping("/api/seller")
public class SellerController {
    Logger logger = LoggerFactory.getLogger(SellerController.class);

    @Autowired
    SellerService sellerService;

    @GetMapping(path="/profile")
    @PreAuthorize("hasAuthority('SELLER')")
    public ResponseEntity<SellerDTO> viewProfile(Authentication authentication) throws IOException {

        logger.info("SellerController: viewProfile started execution");
        SellerDTO seller = sellerService.showSellerProfile(authentication.getName());
        logger.info("SellerController: viewProfile ended execution ");
        return new ResponseEntity<>(seller, HttpStatus.OK);
    }

    @PatchMapping(path = "/update_profile")
    @PreAuthorize("hasAuthority('SELLER')")
    public ResponseEntity<String> updateProfile(Authentication authentication
            ,@RequestBody @Valid SellerUpdateDTO sellerDTO){
        logger.info("SellerController: updateProfile started execution");
        String response = sellerService.updateProfile(authentication.getName(), sellerDTO);
        logger.info("SellerController: updateProfile ended execution ");
        return new ResponseEntity<>(response,HttpStatus.OK);
    }

    @PatchMapping(path="/change_password")
    @PreAuthorize("hasAuthority('SELLER')")
    public ResponseEntity<String> changePassword(Authentication authentication
            ,@Valid @RequestBody PasswordDTO passwordDTO){
        if(!passwordDTO.getPassword().equals(passwordDTO.getConfirmPassword())) {
            throw new PasswordDoNotMatchException("Password do not match.");
        }
            String response = sellerService.updatePassword(authentication.getName(), passwordDTO.getPassword());
        return new ResponseEntity<>(response,HttpStatus.OK);
    }

    @PatchMapping("/update_address")
    @PreAuthorize("hasAuthority('SELLER')")
    public ResponseEntity<String> updateAddress(Authentication authentication,@Valid @RequestBody AddressUpdateDTO addressDTO){
        String email = authentication.getName();
        String responseMessage = sellerService.updateAddress(email,addressDTO);
        return new ResponseEntity<>(responseMessage,HttpStatus.OK);
    }
}
